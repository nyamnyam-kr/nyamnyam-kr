package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.GroupEntity;
import kr.nyamnyam_kr.model.entity.GroupReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    @Override
    GroupEntity save(GroupEntity groupEntity);
}
