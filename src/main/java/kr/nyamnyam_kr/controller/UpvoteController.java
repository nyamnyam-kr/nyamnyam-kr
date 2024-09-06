package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.entity.UpvoteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/upvote")
public class UpvoteController {
    
    public List<UpvoteEntity> findAll() {
        return List.of();
    }

    public UpvoteEntity findById(Long id) {
        return null;
    }

    public Boolean save(UpvoteEntity entity) {
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
