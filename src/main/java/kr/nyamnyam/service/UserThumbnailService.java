package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserThumbnailService {
    void uploadThumbnail(UserEntity user, List<MultipartFile> images);
}
