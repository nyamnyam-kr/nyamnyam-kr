package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
