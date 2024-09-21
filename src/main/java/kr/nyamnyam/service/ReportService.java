package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.NoticeModel;
import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.ReportEntity;

import java.util.List;

public interface ReportService {

    List<ReportEntity> findAll();

    ReportEntity findById(Long id);

    Boolean existsById(Long id);

    Long count();

    ReportEntity save(ReportModel model);
}
