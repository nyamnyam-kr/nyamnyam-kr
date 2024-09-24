package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.service.ImageService;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController{
    private final ImageService service;

    @GetMapping("/fileName")
    public ResponseEntity<String> getFileName(@RequestParam String fileName) {
        return ResponseEntity.ok(service.getFileName(fileName));
    }

    @PostMapping("/upload/{postId}")
    public ResponseEntity<List<ImageModel>> uploadFiles(@RequestPart("files") List<MultipartFile> multipartFiles,
                                                        @PathVariable Long postId) {
        return ResponseEntity.ok(service.uploadFiles(multipartFiles, postId));
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ImageEntity>> findByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(service.findByPostId(postId));
    }
    @GetMapping("/post/{postId}/imageIds")
    public ResponseEntity<List<Long>> findImageIdsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(service.findImageIdsByPostId(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ImageEntity>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/exists/{postId}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long postId) {
        return ResponseEntity.ok(service.existsById(postId));
    }
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long imageId) {
        return ResponseEntity.ok(service.deleteById(imageId));
    }
    @GetMapping("/group")
    public ResponseEntity<List<ImageEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
    @PutMapping("/{postId}")
    public ResponseEntity<Boolean> updateImages(@PathVariable Long postId,@RequestPart("files") List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(service.updateImages(postId, multipartFiles));
    }
}