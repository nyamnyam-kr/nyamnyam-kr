package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneEntity,Long> {
}
