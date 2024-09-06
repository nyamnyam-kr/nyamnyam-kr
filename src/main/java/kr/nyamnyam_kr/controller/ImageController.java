package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.entity.ImageEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class ImageController {
    
    public List<ImageEntity> findAll() {
        return List.of();
    }

    public ImageEntity findById(Long id) {
        return null;
    }

    public Boolean save(ImageEntity entity) {
        return null;
    }

    public Boolean deleteById(Long id) {
        return null;
    }
    
    public Boolean existsById(Long id) {
        return null;
    }

    
    public long count() {
        return 0;
    }
}
