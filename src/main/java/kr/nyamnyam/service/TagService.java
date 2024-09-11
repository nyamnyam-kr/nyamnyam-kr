package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<TagEntity> findAll();

    Optional<TagEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Boolean save(TagEntity entity);
}
