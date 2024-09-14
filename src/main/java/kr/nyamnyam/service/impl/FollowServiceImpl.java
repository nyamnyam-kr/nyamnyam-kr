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
    public void follow(Long followerId, Long followingId) {
        // 이미 팔로우 관계가 있는지 확인
        if (followRepository.findByFollowerIdAndFollowingId(followerId, followingId).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 관계가 존재합니다.");
        }

        // 팔로우 관계 저장
        FollowsEntity followsEntity = FollowsEntity.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();

        followRepository.save(followsEntity);
    }

    @Transactional
    @Override
    public void unfollow(Long followerId, Long followingId) {
        // 팔로우 관계가 존재하는지 확인
        FollowsEntity followsEntity = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        // 팔로우 관계 삭제
        followRepository.delete(followsEntity);
    }
}
