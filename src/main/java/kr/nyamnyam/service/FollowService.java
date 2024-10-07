package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.Follow;
import reactor.core.publisher.Mono;

public interface FollowService {
    Mono<Follow> follow(String followerId, String followeeId);
    Mono<Void> unfollow(String followerId, String followeeId);
    Mono<Boolean> isFollowing(String followerId, String followeeId);
}