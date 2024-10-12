package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.FollowsEntity;
import kr.nyamnyam.model.repository.FollowRepository;
import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Transactional
    @Override
    public Boolean follow(String follower, String following) {
        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 관계가 존재합니다.");
        }

        FollowsEntity followsEntity = FollowsEntity.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(followsEntity);
        return true;
    }

    @Transactional
    @Override
    public Boolean unfollow(String follower, String following) {
        FollowsEntity followsEntity = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(followsEntity);
        return true;
    }

    @Override
    public List<FollowsEntity> findMyFollower(String nickname) {
        return followRepository.findByFollower(nickname);
    }

    @Override
    public List<FollowsEntity> findMyFollowing(String nickname) {
        return followRepository.findByFollowing(nickname);
    }


}
