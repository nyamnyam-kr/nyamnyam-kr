package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.UpvoteEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.UpvoteRepository;
import kr.nyamnyam.service.UpvoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpvoteServiceImpl implements UpvoteService {
    private final UpvoteRepository repository;
    private final PostRepository postRepository;

    @Override
    public boolean like(Long postId, Long userId) {
        UpvoteEntity save = repository.save(postId, userId);
        System.out.println(save);
        return true;

    }

    @Override
    public boolean unlike(Long postId, Long userId) {
        UpvoteEntity upvote = repository.findByPostIdAndGiveId(postId,userId)
                .orElse(null);

        if(upvote != null) {
            repository.delete(upvote);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLiked(Long postId, Long userId) {
        return repository.existsByPostIdAndGiveId(postId, userId);
    }

    @Override
    public int getLikeCount(Long postId) {
        return repository.countByPostId(postId);
    }
}
