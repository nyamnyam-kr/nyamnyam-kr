package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    ReportEntity findByUserIdAndPostId(Long userId, Long id);

    ReportEntity findByUserId(Long id);

}
