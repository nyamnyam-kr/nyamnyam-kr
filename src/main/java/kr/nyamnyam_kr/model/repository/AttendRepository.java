package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.AttendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository <AttendEntity, Long> {
}
