package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService service;

    @GetMapping("/group")
    public ResponseEntity<List<ImageEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ImageEntity>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
    @PutMapping("")
    public ResponseEntity<Boolean> update(@RequestBody ImageEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody ImageEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
