package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ZoneModel;
import kr.nyamnyam.model.entity.ZoneEntity;
import kr.nyamnyam.model.repository.ZoneRepository;
import kr.nyamnyam.service.ZoneService;
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
