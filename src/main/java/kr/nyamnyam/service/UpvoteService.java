package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.UpvoteEntity;

import java.util.List;
import java.util.Optional;

public interface UpvoteService {
    boolean like(Long postId, Long userId);

    boolean unlike(Long postId, Long userId);

    boolean hasLiked(Long postId, Long userId);

    int getLikeCount(Long postId);
}
