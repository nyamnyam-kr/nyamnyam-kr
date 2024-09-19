package kr.nyamnyam.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.nyamnyam.model.entity.UsersThumbnailEntity;

public interface UserThumbnailRepository extends JpaRepository<UsersThumbnailEntity, Long> {

}
