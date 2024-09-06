package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.model.entity.UpvoteEntity;
import kr.nyamnyam_kr.service.UpvoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpvoteServiceImpl implements UpvoteService {
    @Override
    public List<UpvoteEntity> findAll() {
        return List.of();
    }

    @Override
    public UpvoteEntity findById(Long id) {
        return null;
    }

    @Override
    public Boolean save(UpvoteEntity entity) {
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
