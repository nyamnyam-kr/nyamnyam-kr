package kr.nyamnyam.service.impl;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.entity.UserEntity;
import kr.nyamnyam.model.entity.UserThumbnailEntity;
import kr.nyamnyam.model.repository.UserThumbnailRepository;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
//@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserThumbnailServiceImpl implements UserThumbnailService {
    private final UserThumbnailRepository userThumbnailRepository;

    @Override
    public void uploadThumbnail(UserEntity user, List<MultipartFile> images) {
        try {
            String uploadsDir = "src/main/resources/static/uploads/thumbnails/";

            for (MultipartFile image : images) {
                // 파일 저장 로직 추가
                String filePath = uploadsDir + image.getOriginalFilename();
                File dest = new File(filePath);
                image.transferTo(dest);

                // 저장된 파일 경로를 UserThumbnailEntity로 저장
                UserThumbnailEntity thumbnail = UserThumbnailEntity.builder()
                        .userEntity(user)
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
