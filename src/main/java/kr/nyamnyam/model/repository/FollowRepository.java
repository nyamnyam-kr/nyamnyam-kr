package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.FollowsEntity;
import kr.nyamnyam.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowsEntity, Long> {
    List<FollowsEntity> findByFollower(UsersEntity follower);
    List<FollowsEntity> findByFollowing(UsersEntity following);
    Optional<FollowsEntity> findByFollowerAndFollowing(UsersEntity follower, UsersEntity following);
}