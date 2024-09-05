package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.model.repository.PostRepository;
import kr.nyamnyam_kr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Override
    public List<?> findAll(Long restId) {
        return repository.findAll();
    }

    @Override
    public PostEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
        return false;
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
