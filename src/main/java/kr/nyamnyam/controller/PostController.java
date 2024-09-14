package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.service.ImageService;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<PostModel>> getPostPage(@PathVariable int page) {
        return ResponseEntity.ok(service.findAllPerPage(page));
    }

    @GetMapping("/group")
    public ResponseEntity<List<PostModel>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostModel> getPostId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> existsPostById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countPost() {
        return ResponseEntity.ok(service.count());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePostById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updatePost(@PathVariable Long id, @RequestBody PostModel model) {
        return ResponseEntity.ok(service.save(model));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createPost(@RequestBody PostModel model) {
        System.out.println("POST DATA: " + model.getTags());
        Boolean result = service.save(model);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
}

    /*@PostMapping("")
    public ResponseEntity<Boolean> write(@RequestPart("post") PostEntity entity, @RequestPart("files") List<MultipartFile> files) {
        service.save(entity);
        imageService.saveImages(files, entity);
        return ResponseEntity.ok(true);
    }*/

