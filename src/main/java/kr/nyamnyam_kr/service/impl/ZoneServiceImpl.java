package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.ZoneModel;
import kr.nyamnyam_kr.model.entity.ZoneEntity;
import kr.nyamnyam_kr.model.repository.ZoneRepository;
import kr.nyamnyam_kr.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;

    @Override
    public ZoneEntity save(ZoneModel ZoneModel) {
        ZoneEntity zoneEntity = new ZoneEntity();
        return zoneRepository.save(zoneEntity);
    }

    @Override
    public List<ZoneEntity> findAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Optional<ZoneEntity> findById(Long id) {
        return zoneRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        zoneRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return zoneRepository.existsById(id);
    }

    @Override
    public long count() {
        return zoneRepository.count();
    }
}
