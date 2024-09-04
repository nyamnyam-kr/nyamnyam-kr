package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.domain.GroupModel;
import kr.nyamnyam_kr.model.entity.GroupEntity;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Boolean save(GroupModel groupModel);

    Boolean update(GroupModel groupModel);

    List<GroupEntity> findAll();

    Optional<GroupEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
