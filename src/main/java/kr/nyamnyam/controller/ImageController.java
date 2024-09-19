package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.service.ImageService;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController{
    private final ImageService service;
    private final PostService postService;

    @GetMapping("/fileName")
    public ResponseEntity<String> getFileName(@RequestParam String fileName) {
        return ResponseEntity.ok(service.getFileName(fileName));
    }

    /*@PostMapping("/uploadSample")
    public ResponseEntity<List<ImageModel>> uploadFilesSample(@RequestParam List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(service.uploadFilesSample(multipartFiles));
    }*/

    @PostMapping("/upload")
    public ResponseEntity<List<ImageModel>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles,
                                                        @RequestParam String uploadPath,
                                                        @RequestParam Long postId) {
        System.out.println("리액트에서 넘어온 postId: " + postId);
        return ResponseEntity.ok(service.uploadFiles(multipartFiles, uploadPath, postId));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> uploadImages(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("postId") Long postId) {
        PostEntity postEntity = postService.findEntityById(postId);
        if(postEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        return ResponseEntity.ok(service.saveImages(files, postEntity));
    }

    @GetMapping("/group")
    public ResponseEntity<List<ImageEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ImageEntity>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
    @PutMapping("")
    public ResponseEntity<Boolean> update(@RequestBody ImageEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
}
