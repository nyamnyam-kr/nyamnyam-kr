package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findByPostId(Long postId);

}

