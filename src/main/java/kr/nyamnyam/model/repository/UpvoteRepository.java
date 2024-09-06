package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.UpvoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpvoteRepository extends JpaRepository<UpvoteEntity, Long> {

}
