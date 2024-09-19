package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.model.entity.UsersThumbnailEntity;
import kr.nyamnyam.model.repository.UserThumbnailRepository;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserThumbnailServiceImpl implements UserThumbnailService {

    private final UserThumbnailRepository userThumbnailRepository;

    @Override
    public void uploadThumbnail(UsersEntity user, List<MultipartFile> images) {
        try {
            String uploadsDir = "src/main/resources/static/uploads/thumbnails/";

            for (MultipartFile image : images) {
                // 파일 저장 로직 추가
                String filePath = uploadsDir + image.getOriginalFilename();
                File dest = new File(filePath);
                image.transferTo(dest);

                // 저장된 파일 경로를 UserThumbnailEntity로 저장
                UsersThumbnailEntity thumbnail = UsersThumbnailEntity.builder()
                        .userId(user.getId())  // userId로 저장
                        .imagePath(filePath)
                        .createdAt(LocalDateTime.now())
                        .build();

                userThumbnailRepository.save(thumbnail);
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
    }
}
