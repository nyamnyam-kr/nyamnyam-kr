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
    public ResponseEntity<String> follow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.follow(followerId, followingId);
        return ResponseEntity.ok("팔로우 성공");
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<String> unfollow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.unfollow(followerId, followingId);
        return ResponseEntity.ok("언팔로우 성공");
    }
}
