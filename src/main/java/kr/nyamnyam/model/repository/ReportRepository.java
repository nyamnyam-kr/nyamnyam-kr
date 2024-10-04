package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {


    ReportEntity findByUserId(Long id);

    ReportEntity findByPostId(Long postId);

}
