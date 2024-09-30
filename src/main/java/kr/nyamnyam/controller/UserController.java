package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/existsById")
    public Mono<Boolean> existsById(@RequestParam String id) { // 반환 타입을 Mono<Boolean>로 변경
        return userService.existsById(id);
    }

    @GetMapping("/findById")
    public Mono<User> findById(@RequestParam String id) { // 반환 타입을 Mono<User>로 변경
        return userService.findById(id);
    }

    @GetMapping("/findAll")
    public Flux<User> findAll() { // 반환 타입을 Flux<User>로 변경
        return userService.findAll();
    }

    @GetMapping("/count")
    public Mono<Long> count() { // 반환 타입을 Mono<Long>로 변경
        return userService.count();
    }

    @DeleteMapping("/deleteById")
    public Mono<Void> deleteById(@RequestParam String id) { // 반환 타입을 Mono<Void>로 변경
        return userService.deleteById(id);
    }

    @PutMapping("/update")
    public Mono<User> update(@RequestBody User user) { // 반환 타입을 Mono<User>로 변경
        return userService.update(user);
    }

    @PostMapping("/join")
    public Mono<User> join(@RequestBody User user) { // 반환 타입을 Mono<User>로 변경
        return userService.save(user);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestParam String username, @RequestParam String password) { // 반환 타입을 Mono<String>로 변경
        return userService.authenticate(username, password);
    }

}
