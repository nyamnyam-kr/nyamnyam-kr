package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Rating;
import kr.nyamnyam.model.repository.RatingRepository;
import kr.nyamnyam.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public Mono<Rating> save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Flux<Rating> findByRatedUserId(String ratedUserId) {
        return ratingRepository.findByRatedUserId(ratedUserId);
    }

    @Override
    public Mono<Double> calculateAverageRating(String ratedUserId) {
        return findByRatedUserId(ratedUserId)
                .map(Rating::getScore)
                .collectList()
                .map(scores -> scores.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0));
    }
}
