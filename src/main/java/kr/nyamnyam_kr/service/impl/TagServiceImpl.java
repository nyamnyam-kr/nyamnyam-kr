package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.TagEntity;
import kr.nyamnyam_kr.model.repository.TagRepository;
import kr.nyamnyam_kr.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    @Override
    public List<TagEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TagEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TagEntity save(TagEntity entity) {
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
