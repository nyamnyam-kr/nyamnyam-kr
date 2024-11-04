package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.entity.PostTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTagEntity, Long> {
    List<PostTagEntity> findByPostId(Long postId);
    void deleteByPostId(Long postId);

    List<PostTagEntity> findByPost(PostEntity postEntity);
}
