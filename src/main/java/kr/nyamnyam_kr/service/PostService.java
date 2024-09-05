package kr.nyamnyam_kr.service;


import kr.nyamnyam_kr.model.domain.PostModel;
import kr.nyamnyam_kr.model.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<?> findAll(Long restId);

    PostEntity findById(Long id);

    Boolean deleteById(Long id);


    Boolean existsById(Long id);

    Long count();
}

