package kr.nyamnyam_kr.service;


import kr.nyamnyam_kr.model.domain.PostModel;
import kr.nyamnyam_kr.model.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostEntity save(PostModel postModel);

    List<PostEntity> findAll();

    Optional<PostEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
