package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Override
    public List<PostEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public PostEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
        return false;
    }

    @Override
    public PostEntity save(PostEntity entity) {
        return repository.save(entity);
    }
}
