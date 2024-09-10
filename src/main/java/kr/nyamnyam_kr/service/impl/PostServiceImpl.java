package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.PostModel;
import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.model.repository.PostRepository;
import kr.nyamnyam_kr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostEntity save(PostModel postModel) {
        PostEntity postEntity = PostEntity.toPostEntity(postModel);
        return postRepository.save(postEntity);
    }

    @Override
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public long count() {
        return postRepository.count();
    }
}
