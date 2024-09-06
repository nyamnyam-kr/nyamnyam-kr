package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.model.repository.ReplyRepository;
import kr.nyamnyam.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/replies")
public class ReplyController {
    private final ReplyService service;
    private final ReplyRepository repository;

    @PostMapping("/{postId}")
    public ResponseEntity<List<?>> findAll(@PathVariable Long postId) {
        return ResponseEntity.ok(service.findAll(postId));
    }

    @GetMapping("")
    public ResponseEntity<ReplyEntity> findById(@RequestBody Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
    @PutMapping("")
    public ResponseEntity<ReplyEntity> update(@RequestBody ReplyEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("/write")
    public ResponseEntity<ReplyEntity> write(@RequestBody ReplyEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
