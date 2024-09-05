package kr.nyamnyam_kr.service;


import kr.nyamnyam_kr.model.domain.ReplyModel;
import kr.nyamnyam_kr.model.entity.ReplyEntity;

import java.util.List;
import java.util.Optional;

public interface ReplyService{

    List<?> findAll(Long postId);

    ReplyEntity findById(Long id);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    Long count();

}

