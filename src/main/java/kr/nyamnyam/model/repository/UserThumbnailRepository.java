package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.UsersThumbnail;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;

@Repository
public interface UserThumbnailRepository extends ReactiveMongoRepository<UsersThumbnail, String> {
    Mono<UsersThumbnail> findByUserId(String userId);
}
