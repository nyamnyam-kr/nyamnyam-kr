package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.Follow;
import kr.nyamnyam.model.repository.FollowRepository;
import kr.nyamnyam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public Mono<Follow> follow(String followerId, String followeeId) {
        return followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .switchIfEmpty(Mono.defer(() -> {
                    Follow follow = Follow.builder()
                            .followerId(followerId)
                            .followeeId(followeeId)
                            .build();
                    return followRepository.save(follow);
                }));
    }

    @Override
    public Mono<Void> unfollow(String followerId, String followeeId) {
        return followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .flatMap(followRepository::delete);
    }

    @Override
    public Mono<Boolean> isFollowing(String followerId, String followeeId) {
        return followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .map(follow -> true)
                .defaultIfEmpty(false);
    }
}
