package kr.nyamnyam.model.domain;

import lombok.Data;

@Data
public class FollowModel {
    private Long id;

    // 나를 팔로우하는 사람
    private Long followerId;

    // 내가 팔로우하는 사람
    private Long followingId;
}
