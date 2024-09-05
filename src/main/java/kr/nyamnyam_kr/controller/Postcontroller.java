package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.model.repository.PostRepository;
import kr.nyamnyam_kr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class Postcontroller {
    private final PostService service;
    private final PostRepository repository;

    @PostMapping("/write")
    public PostEntity write(@RequestBody PostEntity postEntity) {
        return repository.save(PostEntity.builder()
                .content(postEntity.getContent())
                .taste(postEntity.getTaste())
                .clean(postEntity.getClean())
                .service(postEntity.getService())
                .entryDate(postEntity.getEntryDate())
                .modifyDate(postEntity.getModifyDate())
                .build());
    }

    // 식당별 findAll
    @GetMapping("/group")
    public ResponseEntity<List<?>> findAll(@RequestParam Long restId, @RequestParam PostEntity postEntity) {
        return ResponseEntity.ok(service.findAll(restId));
    }

    @PostMapping("")
    public ResponseEntity<PostEntity> findById(@RequestBody Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("")
    public ResponseEntity<Boolean> existsById(@RequestParam Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}
