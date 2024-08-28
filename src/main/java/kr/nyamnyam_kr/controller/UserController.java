package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.model.repository.UserRepository;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController     //rest가 된 순간 바깥으로도 데이터가 나갈 수 있다.
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;


    @PostMapping("join")
    public Object join(@RequestBody UserModel model) {

      return userService.save(model);

    }

    @PostMapping("login")
    public UserModel login(@RequestBody UserModel model) {

        return userService.findByUsernameAndPassword(model.getUsername(), model.getPassword());
    }

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
