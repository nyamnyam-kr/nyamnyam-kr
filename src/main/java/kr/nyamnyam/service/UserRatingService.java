package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.UserRating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRatingService {
    Mono<UserRating> save(UserRating userRating);
    Flux<UserRating> findByRatedUserId(String ratedUserId);
    Mono<Double> calculateAverageRating(String ratedUserId);
}
