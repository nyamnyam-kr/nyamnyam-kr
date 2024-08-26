package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;

    @PostMapping("save")
    public UserEntity save(UserModel userModel) {
        return userService.save(userModel);
    }

    @GetMapping("findAll")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("findById")
    public Optional<UserEntity> findById(Long id) {
        return userService.findById(id);
    }

    @GetMapping("deleteById")
    public void deleteById(Long id) {
        userService.deleteById(id);
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return userService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return userService.count();
    }
}
