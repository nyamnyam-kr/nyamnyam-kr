package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.domain.Chart.ReportCountModel;

import java.util.List;

public interface ReportRepositoryCustom {

    List<ReportCountModel> reportFindAll();
}
