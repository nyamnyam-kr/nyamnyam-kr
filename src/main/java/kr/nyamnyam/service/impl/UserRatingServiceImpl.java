package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.UserRating;
import kr.nyamnyam.model.repository.UserRatingRepository;
import kr.nyamnyam.service.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRatingServiceImpl implements UserRatingService {

    private final UserRatingRepository userRatingRepository;

    @Override
    public Mono<UserRating> save(UserRating userRating) {
        return userRatingRepository.save(userRating);
    }

    @Override
    public Flux<UserRating> findByRatedUserId(String ratedUserId) {
        return userRatingRepository.findByRatedUserId(ratedUserId);
    }

    @Override
    public Mono<Double> calculateAverageRating(String ratedUserId) {
        return findByRatedUserId(ratedUserId)
                .map(UserRating::getScore)
                .collectList()
                .map(scores -> scores.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0));
    }
}
