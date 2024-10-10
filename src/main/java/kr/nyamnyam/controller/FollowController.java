package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.Follow;
import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public Mono<Follow> follow(@RequestParam String followerId, @RequestParam String followeeId) {
        return followService.follow(followerId, followeeId);
    }

    @DeleteMapping("/unfollow")
    public Mono<Void> unfollow(@RequestParam String followerId, @RequestParam String followeeId) {
        return followService.unfollow(followerId, followeeId);
    }

    @GetMapping("/isFollowing")
    public Mono<Boolean> isFollowing(@RequestParam String followerId, @RequestParam String followeeId) {
        return followService.isFollowing(followerId, followeeId);
    }
}
