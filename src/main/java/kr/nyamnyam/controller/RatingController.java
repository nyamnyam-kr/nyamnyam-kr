package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.Rating;
import kr.nyamnyam.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/rate")
    public Mono<Rating> rate(@RequestBody Rating rating) {
        return ratingService.save(rating);
    }

    @GetMapping("/user/{ratedUserId}")
    public Flux<Rating> getUserRatings(@PathVariable String ratedUserId) {
        return ratingService.findByRatedUserId(ratedUserId);
    }

    @GetMapping("/average/{ratedUserId}")
    public Mono<Double> getAverageRating(@PathVariable String ratedUserId) {
        return ratingService.calculateAverageRating(ratedUserId);
    }
}
