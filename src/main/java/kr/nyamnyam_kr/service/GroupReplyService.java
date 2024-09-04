package kr.nyamnyam_kr.service;


import kr.nyamnyam_kr.model.domain.GroupReplyModel;
import kr.nyamnyam_kr.model.entity.GroupReplyEntity;

import java.util.List;
import java.util.Optional;

public interface GroupReplyService {
    GroupReplyEntity save(Long userId, GroupReplyModel groupReplyModel,Long groupId);

    List<GroupReplyEntity> findAll(Long groupId);

    Optional<GroupReplyEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
