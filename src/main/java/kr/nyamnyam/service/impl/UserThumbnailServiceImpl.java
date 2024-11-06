package kr.nyamnyam.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.model.domain.UsersThumbnail;
import kr.nyamnyam.model.repository.UserThumbnailRepository;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserThumbnailServiceImpl implements UserThumbnailService {

    private final UserThumbnailRepository userThumbnailRepository;
    private final AmazonS3 amazonS3;

    @Value("${naver.storage.bucket}")
    private String bucketName;

    @Value("${naver.storage.upload.path}")
    private String uploadPath;

    @Override
    public Mono<String> uploadThumbnail(String userId, List<MultipartFile> images) {
        return Flux.fromIterable(images)
                .next() // 첫 번째 이미지에 대해서만 작업
                .flatMap(image -> Mono.fromCallable(() -> {
                            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                            String keyName = uploadPath + "/" + fileName;

                            // 메타데이터 설정
                            ObjectMetadata metadata = new ObjectMetadata();
                            metadata.setContentLength(image.getSize());
                            metadata.setContentType(image.getContentType());

                            // S3에 파일 업로드
                            try (InputStream inputStream = image.getInputStream()) {
                                amazonS3.putObject(new PutObjectRequest(bucketName, keyName, inputStream, metadata)
                                        .withCannedAcl(CannedAccessControlList.PublicRead));
                            } catch (Exception e) {
                                throw new RuntimeException("S3 업로드 실패: " + e.getMessage());
                            }

                            // 업로드된 파일의 URL 생성
                            return "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
                        })
                        .flatMap(url -> userThumbnailRepository.save(
                                        UsersThumbnail.builder()
                                                .userId(userId)
                                                .thumbnailUrl(url)
                                                .createdAt(LocalDateTime.now().toEpochSecond(null))
                                                .build())
                                .thenReturn(url))
                        .onErrorResume(e -> Mono.error(new RuntimeException("Failed to save thumbnail: " + e.getMessage()))))
                .switchIfEmpty(Mono.error(new RuntimeException("No thumbnail provided")));
    }


}

