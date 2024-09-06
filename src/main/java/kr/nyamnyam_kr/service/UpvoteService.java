package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.model.entity.UpvoteEntity;

import java.util.List;

public interface UpvoteService {
    List<UpvoteEntity> findAll();

    UpvoteEntity findById(Long id);

    Boolean save(UpvoteEntity entity);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    long count();
}
