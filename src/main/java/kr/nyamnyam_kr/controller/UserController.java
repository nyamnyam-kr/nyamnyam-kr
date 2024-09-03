package kr.nyamnyam_kr.controller;

import jakarta.servlet.http.HttpSession;
import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import kr.nyamnyam_kr.model.repository.UserRepository;
import kr.nyamnyam_kr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController     //rest가 된 순간 바깥으로도 데이터가 나갈 수 있다.
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegister() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(UserModel userModel, RedirectAttributes redirectAttributes) {
        if (userService.existsByUsername(userModel.getUsername()) && userService.existsByNickname(userModel.getNickname())) {

            userService.save(userModel);
            System.out.println("회원가입 성공!!!");
        } else if (!userService.existsByUsername(userModel.getUsername())) {
            redirectAttributes.addFlashAttribute("message", "아이디가 중복되었습니다.");

            return "redirect:/showMessage";
        } else {
            redirectAttributes.addFlashAttribute("message", "닉네임이 중복되었습니다.");

            return "redirect:/showMessage";
        }
        return "redirect:/";
    }



    @PostMapping("join")
    public Object join(@RequestBody UserModel model) {

      return userService.save(model);

    }

    @PostMapping("login")
    public String login(UserModel userModel, HttpSession session) {
            if(userService.findByUsernameAndPassword(userModel.getUsername(), userModel.getPassword()) != null) {
                session.setAttribute("logIn", userService.findByUsernameAndPassword(userModel.getUsername(), userModel.getPassword()));
                return "redirect:/board/showAll";
            }
        return "redirect:/";
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
