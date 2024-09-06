package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<TagEntity> findAll();

    Optional<TagEntity> findById(Long id);

    TagEntity save(TagEntity entity);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    Long count();

}
