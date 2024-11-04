package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.model.repository.Custom.ReplyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>, ReplyRepositoryCustom {

    List<ReplyEntity> findByPostId(Long postId);

}

