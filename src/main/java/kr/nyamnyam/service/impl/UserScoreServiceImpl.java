package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.UserScore;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.model.repository.UserScoreRepository;
import kr.nyamnyam.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserScoreServiceImpl implements UserScoreService {

    private final UserScoreRepository userScoreRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<Void> scoreUp(String userId) {
        return userScoreRepository.save(UserScore.builder()
                        .userId(userId)
                        .score(0.1)
                        .build())
                .then(userRepository.findById(userId)
                        .flatMap(user -> {
                            user.setScore(user.getScore() + 0.1);
                            return userRepository.save(user);
                        }))
                .then();
    }

    @Override
    public Flux<UserScore> findByUserId(String userId) {
        return userScoreRepository.findByUserId(userId);
    }

    @Override
    public Mono<Double> calculateUserAverageScore(String scoreUserId) {
        return findByUserId(scoreUserId)
                .map(UserScore::getScore)
                .collectList()
                .map(scores -> scores.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0));
    }
}
