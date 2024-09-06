package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.entity.ImageEntity;
import kr.nyamnyam_kr.model.entity.TagEntity;

import java.util.List;

public interface ImageService {
    List<ImageEntity> findAll();

    ImageEntity findById(Long id);

    Boolean save(ImageEntity entity);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    long count();
}
