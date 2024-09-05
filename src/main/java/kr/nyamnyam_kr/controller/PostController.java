package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.PostModel;
import kr.nyamnyam_kr.model.entity.PostEntity;
import kr.nyamnyam_kr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/")
public class PostController {
    private final PostService postService;

    @PostMapping("save")
    public PostEntity save(PostModel postModel) {
        return postService.save(postModel);
    }

    @GetMapping("findAll")
    public List<PostEntity> findAll() {
        return postService.findAll();
    }

    @GetMapping("findById")
    public Optional<PostEntity> findById(Long id) {
        return postService.findById(id);
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        postService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return postService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return postService.count();
    }
}
