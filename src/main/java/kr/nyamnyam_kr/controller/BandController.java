package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.BandModel;
import kr.nyamnyam_kr.model.entity.BandEntity;
import kr.nyamnyam_kr.service.BandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
@CrossOrigin
public class BandController {

    private final BandService bandService;


    
    public BandEntity save(BandModel bandModel) {
        return null;
    }

    
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(bandService.findAll());
    }

    
    public Optional<BandEntity> findById(Long id) {
        return Optional.empty();
    }

    
    public boolean deleteById(Long id) {
        return false;
    }

    
    public boolean existsById(Long id) {
        return false;
    }

    
    public long count() {
        return 0;
    }
}
