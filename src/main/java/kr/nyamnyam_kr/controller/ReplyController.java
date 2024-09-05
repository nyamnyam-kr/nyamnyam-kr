package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.entity.ReplyEntity;
import kr.nyamnyam_kr.model.repository.ReplyRepository;
import kr.nyamnyam_kr.service.ReplyService;
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

    @PostMapping("/write")
    public ReplyEntity write(@RequestBody ReplyEntity replyEntity) {
        return repository.save(ReplyEntity.builder()
                .content(replyEntity.getContent())
                .entryDate(replyEntity.getEntryDate())
                .modifyDate(replyEntity.getModifyDate())
                .build());
    }
    // post별 리스트
    @PostMapping("/{postId}")
    public ResponseEntity<List<?>> findAll(@PathVariable Long postId) {
        return ResponseEntity.ok(service.findAll(postId));
    }

    @PostMapping("")
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
}
