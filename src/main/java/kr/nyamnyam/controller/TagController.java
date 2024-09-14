package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;

    @GetMapping("/category")
    public ResponseEntity<Map<String ,List<TagEntity>>> getTagsByCategory() {
        return ResponseEntity.ok(service.getTagsByCategory());
    }
    @GetMapping("/group")
    public ResponseEntity<List<TagEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<TagEntity>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        return ResponseEntity.ok(service.existsByName(name));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Boolean> deleteByName(@PathVariable String name) {
        return ResponseEntity.ok(service.deleteByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable String name, @RequestBody TagEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody TagEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
