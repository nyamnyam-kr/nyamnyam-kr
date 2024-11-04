package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.FollowModel;
import kr.nyamnyam.model.entity.FollowsEntity;
import kr.nyamnyam.model.repository.FollowRepository;
import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.nyamnyam.model.entity.QFollowsEntity.followsEntity;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Transactional
    @Override
    public Boolean follow(FollowModel follow) {
        if (findFollowingByFollower(follow.getFollower(), follow.getFollowing())) {
            return false;
        }

        FollowsEntity followsEntity = FollowsEntity.builder()
                .follower(follow.getFollower())
                .following(follow.getFollowing())
                .build();

        followRepository.save(followsEntity);
        return true;
    }


    @Transactional
    @Override
    public Boolean unfollow(String follower, String following) {
        Optional<FollowsEntity> followEntityOpt = followRepository.findByFollowerAndFollowing(follower, following);
        if (followEntityOpt.isPresent()) {
            Long id = followEntityOpt.get().getId();
            followRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<FollowsEntity> findMyFollower(String nickname) {
        return followRepository.findAllByFollower(nickname);
    }

    @Override
    public List<FollowsEntity> findMyFollowing(String nickname) {
        return followRepository.findAllByFollowing(nickname);
    }

    @Override
    public Boolean findFollowingByFollower(String follower, String following) {
        List<FollowsEntity> byFollowing = followRepository.findByFollowing(following);
        return byFollowing.stream()
                .anyMatch(entity -> entity.getFollower().equals(follower));
    }


}
