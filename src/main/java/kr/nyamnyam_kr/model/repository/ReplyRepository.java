package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity,Long> {
}
