package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {

    List<ImageEntity> findAll();

    Optional<ImageEntity> findById(UUID id);

    Boolean existsById(UUID id);

    Long count();

    Boolean deleteById(UUID id);

    Boolean save(ImageEntity entity);

    Boolean saveImages(List<MultipartFile> files, PostEntity post);

}
