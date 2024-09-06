package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.UpvoteEntity;
import kr.nyamnyam_kr.model.repository.UpvoteRepository;
import kr.nyamnyam_kr.service.UpvoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpvoteServiceImpl implements UpvoteService {
    private final UpvoteRepository repository;

    @Override
    public List<UpvoteEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UpvoteEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UpvoteEntity save(UpvoteEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
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
