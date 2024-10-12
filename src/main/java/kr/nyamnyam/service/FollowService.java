package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.FollowsEntity;

import java.util.List;

public interface FollowService {
    Boolean follow(String follower, String following);
    Boolean unfollow(String follower, String following);

    List<FollowsEntity> findMyFollower(String nickname);

    List<FollowsEntity> findMyFollowing(String nickname);
}