package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.UpvoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpvoteRepository extends JpaRepository<UpvoteEntity, Long> {

    boolean existsByPostIdAndGiveId(Long postId, Long userId);

    Optional<UpvoteEntity> findByPostIdAndGiveId(Long postId, Long userId);

    int countByPostId(Long postId);
}
