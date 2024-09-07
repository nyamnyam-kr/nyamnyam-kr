package kr.nyamnyam.controller;

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
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService service;

    @GetMapping("/group")
    public ResponseEntity<List<ReplyEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReplyEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/exist/{id}")
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
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody ReplyEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> write(@RequestBody ReplyEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
