package kr.nyamnyam.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
        System.out.println("Starting thumbnail upload for userId: " + userId);

        // images가 null이거나 비어있을 경우 즉시 빈 Mono 반환
        if (images == null || images.isEmpty()) {
            System.out.println("No thumbnail provided. Skipping upload.");
            return Mono.empty();
        }

        return Flux.fromIterable(images)
                .next()
                .flatMap(image -> Mono.fromCallable(() -> {
                                    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                                    String keyName = uploadPath + "/" + fileName;

                                    System.out.println("Uploading to S3 - Key: " + keyName);

                                    ObjectMetadata metadata = new ObjectMetadata();
                                    metadata.setContentLength(image.getSize());
                                    metadata.setContentType(image.getContentType());

                                    try (InputStream inputStream = image.getInputStream()) {
                                        amazonS3.putObject(new PutObjectRequest(bucketName, keyName, inputStream, metadata)
                                                .withCannedAcl(CannedAccessControlList.PublicRead));
                                        System.out.println("Upload to S3 successful - Key: " + keyName);
                                    } catch (Exception e) {
                                        System.err.println("S3 upload failed: " + e.getMessage());
                                        throw new RuntimeException("S3 업로드 실패: " + e.getMessage());
                                    }

                                    return "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
                                })
                                .flatMap(url -> {
                                    System.out.println("Saving thumbnail URL to database - URL: " + url);
                                    return userThumbnailRepository.save(
                                            UsersThumbnail.builder()
                                                    .userId(userId)
                                                    .thumbnailUrl(url)
                                                    .createdAt(LocalDateTime.now().toEpochSecond(null))
                                                    .build()
                                    ).thenReturn(url);
                                })
                                .doOnError(e -> System.err.println("Error saving thumbnail: " + e.getMessage()))
                                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to save thumbnail: " + e.getMessage())))
                );
    }

}
