package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.entity.ImageEntity;
import kr.nyamnyam_kr.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public List<ImageEntity> findAll() {
        return List.of();
    }

    @Override
    public ImageEntity findById(Long id) {
        return null;
    }

    @Override
    public Boolean save(ImageEntity entity) {
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
