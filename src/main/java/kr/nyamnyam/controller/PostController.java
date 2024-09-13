package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.service.ImageService;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
    private final ImageService imageService;

    @GetMapping("/crawling")
    public ResponseEntity<Boolean> crawl() {
        return ResponseEntity.ok(service.crawling());
    }

    @GetMapping("/group/{page}")
    public ResponseEntity<List<PostEntity>> group(@PathVariable int page) {
        System.out.println("넘어온 페이지: " + page);
        return ResponseEntity.ok(service.findAllPerPage(page));
    }

    @GetMapping("/group")
    public ResponseEntity<List<PostEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> findById(@PathVariable Long id) {
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
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody PostEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> write(@RequestPart("post") PostEntity entity, @RequestPart("files") List<MultipartFile> files) {
        service.save(entity);
        imageService.saveImages(files, entity);
        return ResponseEntity.ok(true);
    }
}
