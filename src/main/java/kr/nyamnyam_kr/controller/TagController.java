package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.entity.TagEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    
    public List<TagEntity> findAll() {
        return List.of();
    }

    public TagEntity findById(Long id) {
        return null;
    }

    public Boolean save(TagEntity entity) {
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
