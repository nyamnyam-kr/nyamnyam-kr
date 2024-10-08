package kr.nyamnyam.controller;

import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followerId}/{followingId}")
    public ResponseEntity<?> follow(@PathVariable Long followerId, @PathVariable Long followingId) {
        return ResponseEntity.ok(followService.follow(followerId, followingId));
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<?> unfollow(@PathVariable Long followerId, @PathVariable Long followingId) {
        return ResponseEntity.ok(followService.unfollow(followerId, followingId));
    }
}
