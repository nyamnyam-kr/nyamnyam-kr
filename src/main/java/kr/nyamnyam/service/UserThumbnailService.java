package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.UsersEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserThumbnailService {
    void uploadThumbnail(UsersEntity user, List<MultipartFile> images);
}
