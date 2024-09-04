package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.GroupEntity;
import kr.nyamnyam_kr.model.entity.GroupReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

}
