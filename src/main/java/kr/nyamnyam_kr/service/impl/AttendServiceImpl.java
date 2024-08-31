package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.AttendModel;
import kr.nyamnyam_kr.model.entity.AttendEntity;
import kr.nyamnyam_kr.model.repository.AttendRepository;
import kr.nyamnyam_kr.service.AttendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendServiceImpl implements AttendService {
    private final AttendRepository attendRepository;


    @Override
    public AttendEntity save(AttendModel attendModel) {
        AttendEntity attendEntity = AttendEntity.builder()
                .build();
        return attendRepository.save(attendEntity);
    }

    @Override
    public List<AttendEntity> findAll() {
        return attendRepository.findAll();
    }

    @Override
    public Optional<AttendEntity> findById(Long id) {
        return attendRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        attendRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return attendRepository.existsById(id);
    }

    @Override
    public long count() {
        return attendRepository.count();
    }
}
