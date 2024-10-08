package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.FollowsEntity;
import kr.nyamnyam.model.repository.FollowRepository;
import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Transactional
    @Override
    public Boolean follow(Long followerId, Long followingId) {
        if (followRepository.findByFollowerIdAndFollowingId(followerId, followingId).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 관계가 존재합니다.");
        }

        FollowsEntity followsEntity = FollowsEntity.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();

        followRepository.save(followsEntity);
        return true;
    }

    @Transactional
    @Override
    public Boolean unfollow(Long followerId, Long followingId) {
        FollowsEntity followsEntity = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(followsEntity);
        return true;
    }
}
