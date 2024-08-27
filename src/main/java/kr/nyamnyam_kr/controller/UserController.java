package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.model.repository.UserRepository;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController     //rest가 된 순간 바깥으로도 데이터가 나갈 수 있다.
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("join")
    public Object join(@RequestBody UserEntity entity) {

      return userRepository.save(entity.builder()
                      .username(entity.getUsername())
                      .password(entity.getPassword())
                      . nickname(entity.getNickname())
                      .name(entity.getName())
                      .gender(entity.getGender())
                      .role(entity.getRole())
                      .tel(entity.getTel())
                      .gender(entity.getGender())
                      .enabled(entity.getEnabled())
              .build());

    }

    @PostMapping("login")
    public Object login(@RequestBody UserModel model) {
        Map<?,?> map= userService.login(model);

        return null;
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
