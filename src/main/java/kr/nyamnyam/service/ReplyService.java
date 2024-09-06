package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.ReplyEntity;

import java.util.List;

public interface ReplyService{

    List<?> findAll(Long postId);

    ReplyEntity findById(Long id);

    Boolean deleteById(Long id);

    Boolean existsById(Long id);

    Long count();

}

