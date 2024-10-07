package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.UserRating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRatingRepository extends ReactiveMongoRepository<UserRating, String> {
    Flux<UserRating> findByRatedUserId(String ratedUserId);
}
