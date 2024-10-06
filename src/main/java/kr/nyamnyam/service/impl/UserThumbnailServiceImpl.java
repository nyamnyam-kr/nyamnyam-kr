package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.model.domain.UsersThumbnail;
import kr.nyamnyam.model.repository.UserThumbnailRepository;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserThumbnailServiceImpl implements UserThumbnailService {

    private final UserThumbnailRepository userThumbnailRepository;

    @Override
    public Mono<Void> uploadThumbnail(User user, List<MultipartFile> images) {
        return Mono.fromRunnable(() -> {
            try {
                String uploadsDir = "src/main/resources/static/uploads/thumbnails/";

                for (MultipartFile image : images) {
                    String filePath = uploadsDir + image.getOriginalFilename();
                    File dest = new File(filePath);
                    image.transferTo(dest);

                    UsersThumbnail thumbnail = UsersThumbnail.builder()
                            .userId(user.getId())
                            .thumbnailUrl(filePath)
                            .createdAt(LocalDateTime.now().toEpochSecond(null))
                            .build();

                    userThumbnailRepository.save(thumbnail).subscribe();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
