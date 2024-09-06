package kr.nyamnyam.service;


import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.entity.ReplyEntity;

import java.util.List;
import java.util.Optional;

public interface ReplyService {
    ReplyEntity save(ReplyModel replyModel);

    List<ReplyEntity> findAll();

    Optional<ReplyEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
