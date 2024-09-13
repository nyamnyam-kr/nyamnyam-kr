package kr.nyamnyam.model.domain;

import lombok.Data;

@Data
public class FollowModel {
    private Long id;
    private Long followerId;
    private Long followingId;
}
