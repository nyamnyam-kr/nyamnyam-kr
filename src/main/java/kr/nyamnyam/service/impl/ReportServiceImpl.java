package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.model.repository.ReportRepository;
import kr.nyamnyam.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;


    @Override
    public List<ReportEntity> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public ReportEntity findById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsById(Long id) {
        return reportRepository.existsById(id);
    }

    @Override
    public Long count() {
        return reportRepository.count();
    }


    @Override
    public ReportEntity save(ReportModel model) {
        ReportEntity report = ReportEntity.builder()
                .content(model.getContent())
                .userId(model.getUserId())
                .entryDate(LocalDateTime.now())
                .build();

        return reportRepository.save(report);
    }
}
