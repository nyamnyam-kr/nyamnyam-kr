package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;

    @GetMapping("/group")
    public ResponseEntity<List<TagEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TagEntity>> findById(@PathVariable Long id) {
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

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody TagEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody TagEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
