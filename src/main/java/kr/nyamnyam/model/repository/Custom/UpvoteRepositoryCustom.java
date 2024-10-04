package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.entity.UpvoteEntity;

public interface UpvoteRepositoryCustom {
    UpvoteEntity save(Long postId, Long giveId);
}
