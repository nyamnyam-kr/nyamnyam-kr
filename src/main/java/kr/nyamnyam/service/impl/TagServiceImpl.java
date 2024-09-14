package kr.nyamnyam.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.model.repository.TagRepository;
import kr.nyamnyam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    @Override
    public Map<String, List<TagEntity>> getTagsByCategory() {
        List<TagEntity> tags = repository.findAll();
        return tags.stream()
                .collect(Collectors.groupingBy(tag -> tag.getTagCategory().getDisplayName()));
    }

    @Override
    public List<TagEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TagEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsById(name);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteByName(String name) {
        try {
            repository.deleteById(name);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public Boolean save(TagEntity entity) {
        return repository.save(entity) != null;
    }
}
