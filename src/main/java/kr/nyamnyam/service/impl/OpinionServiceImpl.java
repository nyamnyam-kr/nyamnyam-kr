package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.OpinionModel;
import kr.nyamnyam.model.entity.OpinionEntity;
import kr.nyamnyam.model.repository.OpinionRepository;
import kr.nyamnyam.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService {

    private final OpinionRepository opinionRepository;


    @Override
    public List<OpinionEntity> findAll() {
        return opinionRepository.findAll();
    }

    @Override
    public OpinionEntity findById(Long id) {
        return opinionRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsById(Long id) {
        return opinionRepository.existsById(id);
    }

    @Override
    public Long count() {
        return opinionRepository.count();
    }


    @Override
    public OpinionEntity save(OpinionModel model) {
        OpinionEntity report = OpinionEntity.builder()
                .content(model.getContent())
                .userId(model.getUserId())
                .entryDate(LocalDateTime.now())
                .build();

        return opinionRepository.save(report);
    }
}
