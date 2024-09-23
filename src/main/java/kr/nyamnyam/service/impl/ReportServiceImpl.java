package kr.nyamnyam.service.impl;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.model.entity.ReportReason;
import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.ReportRepository;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;


    @Override
    public Boolean save(ReportModel model) {
        Long userId = model.getUserId();
        Long postId = model.getPostId();

        if (model.getId() == null) {

            ReportEntity reportEntity = ReportEntity.builder()
                    .userId(userId)
                    .postId(postId)
                    .reason(model.getReason())
                    .build();
            reportRepository.save(reportEntity);
        } else {

            ReportEntity reportEntity = reportRepository.findByUserId(userId);

            if (reportEntity != null && reportEntity.getPostId().equals(postId)) {
                reportEntity.setReason(model.getReason());
                reportRepository.save(reportEntity);
            } else {
                ReportEntity report = ReportEntity.builder()
                        .userId(userId)
                        .postId(postId)
                        .reason(model.getReason())
                        .build();
                reportRepository.save(report);
            }
        }
        return true;
    }

}
