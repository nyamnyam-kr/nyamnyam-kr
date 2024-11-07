package kr.nyamnyam.controller;

import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/thumbnails")
public class UserThumbnailController {

    private final UserThumbnailService userThumbnailService;

    @PostMapping("/upload")
    public Mono<ResponseEntity<String>> uploadThumbnail(
            @RequestParam("userId") String userId,
            @RequestPart("images") List<MultipartFile> images) { // @RequestPart로 변경

        System.out.println("Received upload request - userId: " + userId + ", image count: " + images.size());

        return userThumbnailService.uploadThumbnail(userId, images)
                .doOnNext(url -> System.out.println("Thumbnail uploaded successfully - URL: " + url))
                .map(url -> ResponseEntity.ok(url)) // 단일 URL 반환
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(e -> System.err.println("Error during thumbnail upload: " + e.getMessage()));
    }
}
