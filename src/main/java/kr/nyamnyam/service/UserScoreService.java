package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.UserScore;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserScoreService {
    Mono<Void> scoreUp(String userId);

    Mono<Void> scoreDown(String userId);

    Flux<UserScore> findByUserId(String userId);

    Mono<Double> calculateUserAverageScore(String scoreUserId);
}
