package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.OpinionEntity;
import kr.nyamnyam.model.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepository extends JpaRepository<OpinionEntity, Long> {
}
