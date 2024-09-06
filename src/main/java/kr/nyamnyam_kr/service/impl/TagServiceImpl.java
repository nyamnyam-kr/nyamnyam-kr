package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.TagEntity;
import kr.nyamnyam_kr.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Override
    public List<TagEntity> findAll() {
        return List.of();
    }

    @Override
    public TagEntity findById(Long id) {
        return null;
    }

    @Override
    public Boolean save(TagEntity entity) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public Boolean existsById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
