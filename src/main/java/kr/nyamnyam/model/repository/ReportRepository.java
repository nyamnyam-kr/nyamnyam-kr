package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.Chart.ReportCountModel;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.model.repository.Custom.ReportRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long>, ReportRepositoryCustom {


    ReportEntity findByUserId(String id);

    ReportEntity findByPostId(Long postId);

}
