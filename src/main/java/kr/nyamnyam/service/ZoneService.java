package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.ZoneModel;
import kr.nyamnyam.model.entity.ZoneEntity;

import java.util.List;
import java.util.Optional;

public interface ZoneService {
    ZoneEntity save(ZoneModel ZoneModel);

    List<ZoneEntity> findAll();

    Optional<ZoneEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
