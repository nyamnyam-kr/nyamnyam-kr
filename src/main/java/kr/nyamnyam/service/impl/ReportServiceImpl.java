package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.model.repository.ReportRepository;
import kr.nyamnyam.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;


    @Override
    public Boolean save(ReportModel model) {
        Long userId = model.getUserId();
        Long postId = model.getPostId();

        ReportEntity byPostId = reportRepository.findByPostId(postId);

        if(byPostId != null && byPostId.getUserId().equals(userId)) {
            byPostId.setReason(model.getReason());
            reportRepository.save(byPostId);

        } else {
            ReportEntity reportEntity = ReportEntity.builder()
                    .userId(userId)
                    .postId(postId)
                    .reason(model.getReason())
                    .build();
            reportRepository.save(reportEntity);
        }

        return true;
    }

    @Override
    public List<ReportEntity> findAll() {
        return reportRepository.findAll();
    }

}
