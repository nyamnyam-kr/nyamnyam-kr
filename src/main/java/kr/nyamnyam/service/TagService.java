package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.TagModel;
import kr.nyamnyam.model.entity.TagEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagService {

    Map<String, List<TagModel>> getTagsByCategory();

    List<TagModel> findAll();

    Boolean save(TagModel model);

    Optional<TagModel> findByName(String name);

    Boolean existsByName(String name);

    Boolean deleteByName(String name);

    List<String> getTagCategory();
}
