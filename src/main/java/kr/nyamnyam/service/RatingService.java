package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.Rating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingService {
    Mono<Rating> save(Rating rating);
    Flux<Rating> findByRatedUserId(String ratedUserId);
    Mono<Double> calculateAverageRating(String ratedUserId); // 평점 평균 계산
}
