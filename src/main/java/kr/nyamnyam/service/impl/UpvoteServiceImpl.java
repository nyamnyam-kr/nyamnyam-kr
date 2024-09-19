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
        if(!repository.existsByPostIdAndGiveId(postId, userId)){
            Long haveId =2L; // 테스트로 haveId 고정값 2 설정 -> 연결 후 아래 코드 적용

            /*Long haveId = postRepository.findById(postId)
                    .map(PostEntity::getUserId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));*/

            UpvoteEntity upvote = UpvoteEntity.builder()
                    .postId(postId)
                    .giveId(userId)
                    .haveId(haveId)
                    .build();
            repository.save(upvote);

            return true;
        } return false;
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
