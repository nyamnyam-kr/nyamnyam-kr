package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UserEntity;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/crawling")
    public ResponseEntity<List<UserEntity>> findCrawling(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(userService.crawling(pageNum, pageSize));
    }

    @GetMapping("/existsById")
    public boolean existsById(@RequestParam Long id) {
        return userService.existsById(id);
    }

    @GetMapping("/findById")
    public Optional<UserEntity> findById(@RequestParam Long id) {
        return userService.findById(id);
    }

    @GetMapping("/findAll")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/count")
    public long count() {
        return userService.count();
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/update")
    public UserEntity update(@RequestBody UserModel userModel) {
        return userService.update(userModel);
    }

    @PostMapping("/join")
    public UserEntity join(@RequestBody UserModel userModel) {
        return userService.save(userModel);
    }


}

