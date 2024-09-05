package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.BandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<BandEntity, Long> {
}
