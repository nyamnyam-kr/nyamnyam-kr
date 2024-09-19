package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.service.ImageService;
import kr.nyamnyam.service.PostService;
import kr.nyamnyam.service.UpvoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final UpvoteService upvoteService;

    @GetMapping("/{restaurantId}/allAverage")
    public ResponseEntity<Double> getAllAverageRating(@PathVariable Long restaurantId){
        double averageRating = service.allAverageRating(restaurantId);
        return ResponseEntity.ok(averageRating);
    }

    // 좋아요 관련 : like, unlike, hasLiked, getLikeCount
    @PostMapping("/{postId}/like")
    public ResponseEntity<Boolean> like(@PathVariable Long postId, @RequestParam Long userId){
        return ResponseEntity.ok(upvoteService.like(postId,userId));
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<Boolean> unlike(@PathVariable Long postId, @RequestParam Long userId){
        return ResponseEntity.ok(upvoteService.unlike(postId,userId));
    }

    @GetMapping("/{postId}/hasLiked")
    public ResponseEntity<Boolean> hasLiked(@PathVariable Long postId, @RequestParam Long userId){
        return ResponseEntity.ok(upvoteService.hasLiked(postId, userId));
    }

    @GetMapping("/{postId}/like-count")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long postId){
        return ResponseEntity.ok( upvoteService.getLikeCount(postId));
    }

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
        PostModel postModel = service.postWithImage(id);
        return ResponseEntity.ok(postModel);
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
        return ResponseEntity.ok(service.updatePost(id, model));
    }

    @PostMapping("")
    public ResponseEntity<Long> createPost(@RequestBody PostModel model) {
        Long postId = service.createPost(model);
        if (postId != null) {
            return ResponseEntity.ok(postId);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

