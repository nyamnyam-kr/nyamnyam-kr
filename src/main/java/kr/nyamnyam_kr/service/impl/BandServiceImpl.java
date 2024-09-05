package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.BandModel;
import kr.nyamnyam_kr.model.entity.BandEntity;
import kr.nyamnyam_kr.service.BandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandServiceImpl implements BandService {
    @Override
    public BandEntity save(BandModel bandModel) {
        return null;
    }

    @Override
    public List<BandEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<BandEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
