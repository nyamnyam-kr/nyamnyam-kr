package kr.nyamnyam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/thumbnails")
public class UserThumbnailController {

    private final UserThumbnailService userThumbnailService;

    @PostMapping("/upload")
    public Mono<ResponseEntity<String>> uploadThumbnail(
            @RequestParam("userId") String userId,
            @RequestParam("images") List<MultipartFile> images) {

        return userThumbnailService.uploadThumbnail(userId, images)
                .map(url -> ResponseEntity.ok(url)) // 단일 URL 반환
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
