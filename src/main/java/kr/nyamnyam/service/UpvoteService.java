package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.UpvoteEntity;

import java.util.List;
import java.util.Optional;

public interface UpvoteService {
    boolean like(Long postId, String userId);

    boolean unlike(Long postId, String userId);

    boolean hasLiked(Long postId, String userId);

    int getLikeCount(Long postId);
}
