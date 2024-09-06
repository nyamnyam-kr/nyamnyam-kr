package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.UpvoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpvoteRepository extends JpaRepository<UpvoteEntity, Long> {

}
