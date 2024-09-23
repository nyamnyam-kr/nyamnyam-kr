package kr.nyamnyam.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/existsById")
    public boolean existsById(@RequestParam Long id) {
        return userService.existsById(id);
    }

    @GetMapping("/findById")
    public Optional<UsersEntity> findById(@RequestParam Long id) {
        return userService.findById(id);
    }

    @GetMapping("/findAll")
    public List<UsersEntity> findAll() {
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
    public UsersEntity update(@RequestBody UserModel userModel) {
        return userService.update(userModel);
    }

    @PostMapping("/join")
    public UsersEntity join(@RequestBody UserModel userModel) {
        return userService.save(userModel);
    }

    @GetMapping("/login/oauth2")
    public String loginWithOAuth2(
            @RequestParam String code,
            @RequestParam String receivedState,
            HttpServletRequest request) {
        return userService.loginWithOAuth2(code, receivedState, request);
    }

    @GetMapping("/startOAuth2")
    public void startOAuth2(HttpServletRequest request, HttpServletResponse response) throws IOException { // 수정
        userService.startOAuth2(request, response); // 수정
    }

}
