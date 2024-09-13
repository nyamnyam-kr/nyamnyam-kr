package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.model.repository.TagRepository;
import kr.nyamnyam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    @Override
    public Map<String, List<TagEntity>> getTagsByCategory() {
        List<TagEntity> tags = repository.findAll();
        return null; // 수업 듣고 다시하기 !!!!!
    }

    @Override
    public List<TagEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TagEntity> findById(Long id) {
        return repository.findById(id);
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean save(TagEntity entity) {
        return repository.save(entity) != null;
    }
}
