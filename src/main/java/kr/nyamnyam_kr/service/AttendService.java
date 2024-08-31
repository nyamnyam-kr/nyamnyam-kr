package kr.nyamnyam_kr.service;


import kr.nyamnyam_kr.model.domain.AttendModel;
import kr.nyamnyam_kr.model.entity.AttendEntity;

import java.util.List;
import java.util.Optional;

public interface AttendService {
    AttendEntity save(AttendModel attendModel);

    List<AttendEntity> findAll();

    Optional<AttendEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
