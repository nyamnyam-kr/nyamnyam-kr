package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")  // 회원가입 엔드포인트
    public UserEntity join(@RequestBody UserModel userModel) {
        return userService.save(userModel);  // 서비스에서 비밀번호 암호화 처리
    }

    @GetMapping("/findAll")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findById")
    public Optional<UserEntity> findById(@RequestParam Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/existsById")
    public boolean existsById(@RequestParam Long id) {
        return userService.existsById(id);
    }

    @GetMapping("/count")
    public long count() {
        return userService.count();
    }
}
