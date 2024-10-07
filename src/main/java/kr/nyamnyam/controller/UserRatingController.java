package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.UserRating;
import kr.nyamnyam.service.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
public class UserRatingController {

    private final UserRatingService userRatingService;

    @PostMapping("/rate")
    public Mono<UserRating> rate(@RequestBody UserRating userRating) {
        return userRatingService.save(userRating);
    }

    @GetMapping("/user/{ratedUserId}")
    public Flux<UserRating> getUserRatings(@PathVariable String ratedUserId) {
        return userRatingService.findByRatedUserId(ratedUserId);
    }

    @GetMapping("/average/{ratedUserId}")
    public Mono<Double> getAverageRating(@PathVariable String ratedUserId) {
        return userRatingService.calculateAverageRating(ratedUserId);
    }
}
