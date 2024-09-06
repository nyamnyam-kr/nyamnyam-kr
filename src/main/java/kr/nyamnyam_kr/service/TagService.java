package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.entity.TagEntity;
import kr.nyamnyam_kr.model.entity.UpvoteEntity;

import java.util.List;

public interface TagService {
    List<TagEntity> findAll();

    TagEntity findById(Long id);

    Boolean save(TagEntity entity);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    long count();
}
