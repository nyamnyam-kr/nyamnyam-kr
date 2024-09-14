package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.TagEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagService {

    Map<String, List<TagEntity>> getTagsByCategory();

    List<TagEntity> findAll();

    Long count();

    Boolean save(TagEntity entity);

    Optional<TagEntity> findByName(String name);

    Boolean existsByName(String name);

    Boolean deleteByName(String name);
}
