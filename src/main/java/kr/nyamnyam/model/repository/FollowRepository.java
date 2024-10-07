package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.Follow;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FollowRepository extends ReactiveMongoRepository<Follow, String> {
    Mono<Follow> findByFollowerIdAndFolloweeId(String followerId, String followeeId);
}
