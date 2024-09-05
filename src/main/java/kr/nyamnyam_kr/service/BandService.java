package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.domain.BandModel;
import kr.nyamnyam_kr.model.entity.BandEntity;

import java.util.List;
import java.util.Optional;

public interface BandService {
    BandEntity save(BandModel bandModel);

    List<BandEntity> findAll();

    Optional<BandEntity> findById(Long id);

    boolean deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
