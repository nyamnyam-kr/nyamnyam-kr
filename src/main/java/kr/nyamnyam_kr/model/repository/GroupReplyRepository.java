package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.GroupReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupReplyRepository extends JpaRepository<GroupReplyEntity,Long> {

}
