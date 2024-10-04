package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.ReportModel;
import kr.nyamnyam.model.entity.ReportEntity;

import java.util.List;

public interface ReportService {

    Boolean save(ReportModel model);

    List<ReportEntity> findAll();
}
