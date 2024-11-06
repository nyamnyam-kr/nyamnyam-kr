package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.service.UserService;
import kr.nyamnyam.service.UserThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserThumbnailService userThumbnailService;

    @GetMapping("/existsById")
    public Mono<Boolean> existsById(@RequestParam String id) {
        return userService.existsById(id);
    }

    @GetMapping("/findById")
    public Mono<User> findById(@RequestParam String id) {
        return userService.findById(id);
    }

    @GetMapping("/findAll")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/count")
    public Mono<Long> count() {
        return userService.count();
    }

    @DeleteMapping("/deleteById")
    public Mono<Void> deleteById(@RequestParam String id) {
        return userService.deleteById(id);
    }

    @PutMapping("/update")
    public Mono<User> update(@RequestPart("user") User user, @RequestPart(value = "thumbnails", required = false) List<MultipartFile> thumbnails) {
        return userService.update(user, thumbnails);
    }

    @PostMapping("/register")
    public Mono<User> register(
            @RequestPart("user") User user,
            @RequestPart(name = "thumbnails", required = false) List<MultipartFile> thumbnails) {

        return userService.save(user)
                .flatMap(savedUser -> {
                    if (thumbnails != null && !thumbnails.isEmpty()) {
                        return userThumbnailService.uploadThumbnail(savedUser.getId(), thumbnails)
                                .flatMap(thumbnailUrl -> { // thumbnailUrl이 단일 String
                                    savedUser.setImgId(thumbnailUrl);
                                    return userService.updateImgIdOnly(savedUser);
                                });
                    }
                    return Mono.just(savedUser);
                });
    }




    @PostMapping("/login")
    public Mono<String> login(@RequestParam String username, @RequestParam String password) {
        return userService.authenticate(username, password);
    }

    @GetMapping("/check-username")
    public Mono<Boolean> checkUsername(@RequestParam String username) {
        return userService.findByUsername(username)
                .hasElement();
    }

    @PutMapping("/toggleEnable")
    public Mono<User> toggleEnable(@RequestParam String userId, @RequestParam Boolean enabled) {
        return userService.setEnableStatus(userId, enabled);
    }

}
