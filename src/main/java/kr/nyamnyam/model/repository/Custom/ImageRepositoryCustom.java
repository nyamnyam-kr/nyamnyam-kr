package kr.nyamnyam.model.repository.Custom;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Long> findImageIdsByPostId(Long postId);
}
