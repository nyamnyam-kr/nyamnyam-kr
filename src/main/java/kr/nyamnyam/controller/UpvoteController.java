package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.model.entity.UpvoteEntity;
import kr.nyamnyam.service.UpvoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/upvote")
public class UpvoteController {
    private final UpvoteService service;

    @PostMapping("/group")
    public ResponseEntity<List<UpvoteEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UpvoteEntity>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @PutMapping("")
    public ResponseEntity<UpvoteEntity> update(@RequestBody UpvoteEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("")
    public ResponseEntity<UpvoteEntity> save(@RequestBody UpvoteEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
