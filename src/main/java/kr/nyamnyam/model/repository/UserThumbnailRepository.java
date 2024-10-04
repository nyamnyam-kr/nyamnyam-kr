package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.UserThumbnailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserThumbnailRepository extends MongoRepository<UserThumbnailEntity, Long> {

}
