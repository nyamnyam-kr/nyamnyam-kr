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
    public Mono<List<String>> uploadThumbnail(User user, List<MultipartFile> images) {
        List<String> thumbnailIds = new ArrayList<>();

        return Flux.fromIterable(images)
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
                            }

                            // 업로드된 파일의 URL 생성
                            String uploadURL = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;

                            // 썸네일 정보 생성
                            return UsersThumbnail.builder()
                                    .userId(user.getId())
                                    .thumbnailUrl(uploadURL)
                                    .createdAt(LocalDateTime.now().toEpochSecond(null))
                                    .build();
                        })
                        .flatMap(thumbnail -> userThumbnailRepository.save(thumbnail)
                                .doOnNext(savedThumbnail -> thumbnailIds.add(savedThumbnail.getId()))
                                .thenReturn(thumbnail.getId()))
                        .onErrorResume(e -> Mono.error(new RuntimeException("Failed to save thumbnail: " + e.getMessage()))))
                .then(Mono.just(thumbnailIds))
                .onErrorResume(e -> Mono.just(Collections.emptyList()));
    }

}
