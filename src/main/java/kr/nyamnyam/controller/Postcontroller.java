package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/posts")
public class Postcontroller {
    private final PostService service;
    private final PostRepository repository;

    @GetMapping("/group")
    public ResponseEntity<List<PostEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("")
    public ResponseEntity<PostEntity> findById(@RequestBody Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> existsById(@RequestParam Long id) {
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
    public ResponseEntity<PostEntity> update(@RequestBody PostEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
    @PostMapping("")
    public ResponseEntity<PostEntity> write(@RequestBody PostEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
