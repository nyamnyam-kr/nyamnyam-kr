package kr.nyamnyam.controller;

import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{follower}/{following}")
    public ResponseEntity<?> follow(@PathVariable String follower, @PathVariable String following) {
        return ResponseEntity.ok(followService.follow(follower, following));
    }

    @DeleteMapping("/{follower}/{following}")
    public ResponseEntity<?> unfollow(@PathVariable String follower, @PathVariable String following) {
        return ResponseEntity.ok(followService.unfollow(follower, following));
    }

    // 나를 팔로우하는 목록 보기
    @GetMapping("/findMyFollower/{nickname}")
    public ResponseEntity<List<?>> fingMyFollower(@PathVariable String nickname) {
        return ResponseEntity.ok(followService.findMyFollower(nickname));
    }

    // 내가 팔로우하는 목록 보기
    @GetMapping("/findMyFollowing/{nickname}")
    public ResponseEntity<List<?>> findMyFollowing(@PathVariable String nickname) {
        return ResponseEntity.ok(followService.findMyFollowing(nickname));
    }
}
