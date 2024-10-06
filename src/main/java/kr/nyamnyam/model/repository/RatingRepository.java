package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.domain.Rating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {
    Flux<Rating> findByRatedUserId(String ratedUserId);
}
