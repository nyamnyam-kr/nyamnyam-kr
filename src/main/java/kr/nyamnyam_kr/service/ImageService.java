package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.entity.ImageEntity;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    List<ImageEntity> findAll();

    Optional<ImageEntity> findById(Long id);

    ImageEntity save(ImageEntity entity);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    Long count();

}
