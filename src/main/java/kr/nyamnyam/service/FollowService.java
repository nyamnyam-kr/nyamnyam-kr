package kr.nyamnyam.service;

public interface FollowService {
    Boolean follow(Long followerId, Long followingId);
    Boolean unfollow(Long followerId, Long followingId);
}