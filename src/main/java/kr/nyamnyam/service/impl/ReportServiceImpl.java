package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Chart.ReportCountModel;
import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.ReportRepository;
import kr.nyamnyam.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;


    @Override
    public Boolean save(ReportModel model) {
        String userId = model.getUserId();
        Long postId = model.getPostId();

        ReportEntity byPostId = reportRepository.findByPostId(postId);

        if(byPostId != null && byPostId.getUserId().equals(userId)) {
            byPostId.setReason(model.getReason());
            reportRepository.save(byPostId);

        } else {
            Optional<PostEntity> optionalPost = postRepository.findById(postId);
            if (optionalPost.isPresent()) {
                PostEntity byId = optionalPost.get();
                ReportEntity reportEntity = ReportEntity.toReportEntity(model, byId);
                reportRepository.save(reportEntity);
            } else {
                return null;
            }

        }

        return true;
    }

    @Override
    public List<ReportModel> findAll() {
        List<ReportEntity> reportEntities = reportRepository.findAll();
        List<ReportModel> reportModelList = new ArrayList<>();

        for (ReportEntity entity : reportEntities) {
            reportModelList.add(entity.toReportModel());
        }

        return reportModelList;
    }

    @Override
    public List<ReportCountModel> reportAll() {
        return reportRepository.reportFindAll();
    }

}
