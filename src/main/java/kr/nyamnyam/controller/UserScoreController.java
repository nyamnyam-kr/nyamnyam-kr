package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.UserScore;
import kr.nyamnyam.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/score")
public class UserScoreController {

    private final UserScoreService userScoreService;

    @PostMapping("/scoreUp")
    public Mono<Void> increaseScore(@RequestParam String userId) {
        return userScoreService.scoreUp(userId);
    }

    @PostMapping("/scoreDown")
    public Mono<Void> decreaseScore(@RequestParam String userId) {
        return userScoreService.scoreDown(userId);
    }

    @GetMapping("/user/{userId}")
    public Flux<UserScore> getUserScores(@PathVariable String userId) {
        return userScoreService.findByUserId(userId);
    }

    @GetMapping("/average/{userId}")
    public Mono<Double> getUserAverageScore(@PathVariable String userId) {
        return userScoreService.calculateUserAverageScore(userId);
    }
}
