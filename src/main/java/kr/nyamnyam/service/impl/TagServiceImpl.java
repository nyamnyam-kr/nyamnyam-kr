package kr.nyamnyam.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kr.nyamnyam.model.domain.TagModel;
import kr.nyamnyam.model.entity.TagCategory;
import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.model.repository.TagRepository;
import kr.nyamnyam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    @Override
    public List<String> getTagCategory() {
        return Arrays.stream(TagCategory.values())
                .map(TagCategory::getDisplayName)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<TagModel>> getTagsByCategory() {
        List<TagEntity> tags = repository.findAll();
        return tags.stream()
                .map(this::convertToModel)
                .collect(Collectors.groupingBy(tag -> tag.getTagCategory().getDisplayName()));
    }

    @Override
    public List<TagModel> findAll() {
        return repository.findAll().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TagModel> findByName(String name) {
        return repository.findByName(name)
                .map(this::convertToModel);
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsById(name);
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
    public Boolean save(TagModel model) {
        TagEntity entity = convertToEntity(model);
        return repository.save(entity) != null;
    }

    private TagModel convertToModel(TagEntity entity){
        return TagModel.builder()
                .id(null)
                .name(entity.getName())
                .tagCategory(entity.getTagCategory())
                .build();
    }

    private TagEntity convertToEntity(TagModel model){
        return new TagEntity(model.getName(), model.getTagCategory());
    }
}
