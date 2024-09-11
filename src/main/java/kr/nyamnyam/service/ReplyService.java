package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.ReplyEntity;

import java.util.List;

public interface ReplyService{

    List<ReplyEntity> findAll();

    ReplyEntity findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Boolean save(ReplyEntity entity);
}

