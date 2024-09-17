package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.TagModel;
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

    @GetMapping("/tag-category")
    public ResponseEntity<List<String>> getTagCategory() {
        return ResponseEntity.ok(service.getTagCategory());
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String ,List<TagModel>>> getTagsByCategory() {
        return ResponseEntity.ok(service.getTagsByCategory());
    }
    @GetMapping("/group")
    public ResponseEntity<List<TagModel>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<TagModel>> findByName(@PathVariable String name) {
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

    @PutMapping("/{name}")
    public ResponseEntity<Boolean> update(@PathVariable String name, @RequestBody TagModel model) {
        return ResponseEntity.ok(service.save(model));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> save(@RequestBody TagModel model) {
        return ResponseEntity.ok(service.save(model));
    }
}
