package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.UpvoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpvoteRepository extends JpaRepository<UpvoteEntity, Long> {

}
