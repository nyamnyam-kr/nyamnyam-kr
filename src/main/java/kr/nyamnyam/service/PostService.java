package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.PostEntity;

import java.util.List;

public interface PostService {

    List<PostEntity> findAll(Long restId);

    PostEntity findById(Long id);

    Boolean deleteById(Long id);


    Boolean existsById(Long id);

    Long count();
}

