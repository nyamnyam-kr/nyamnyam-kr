package kr.nyamnyam.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.nyamnyam.model.entity.UserThumbnailEntity;

public interface UserThumbnailRepository extends JpaRepository<UserThumbnailEntity, Long> {

}
