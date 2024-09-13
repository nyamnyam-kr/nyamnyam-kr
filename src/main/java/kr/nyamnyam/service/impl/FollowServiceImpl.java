package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.FollowsEntity;
import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.model.repository.FollowRepository;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void follow(Long followerId, Long followingId) {
        UsersEntity follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로워 유저가 존재하지 않습니다."));
        UsersEntity following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 유저가 존재하지 않습니다."));

        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 관계가 존재합니다.");
        }

        FollowsEntity followsEntity = FollowsEntity.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(followsEntity);
    }

    @Transactional
    @Override
    public void unfollow(Long followerId, Long followingId) {
        UsersEntity follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로워 유저가 존재하지 않습니다."));
        UsersEntity following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 유저가 존재하지 않습니다."));

        FollowsEntity followsEntity = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(followsEntity);
    }
}