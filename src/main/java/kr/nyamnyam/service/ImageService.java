package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    List<ImageEntity> findAll();

    Optional<ImageEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Boolean save(ImageEntity entity);

    Boolean saveImages(List<MultipartFile> files, PostEntity post);

}
