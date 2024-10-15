package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.FollowsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowsEntity, Long> {
    // 팔로워 ID로 팔로우 관계 조회
    List<FollowsEntity> findAllByFollower(String follower);

    // 팔로잉 ID로 팔로우 관계 조회
    List<FollowsEntity> findAllByFollowing(String following);

    FollowsEntity findByFollower(String follower);

    List<FollowsEntity> findByFollowing(String following);

    Optional<FollowsEntity> findByFollowerAndFollowing(String follower, String following);
}
