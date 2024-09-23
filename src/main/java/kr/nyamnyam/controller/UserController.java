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
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/user")

public class UserController {

    private final UserService userService;

    @GetMapping("/existsById")
    public Boolean existsById(@RequestParam Long id) {
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
    public Long count() {
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

    @PostMapping("/login")
    public Optional<UsersEntity> login(@RequestBody UserModel userModel) {
        return userService.login(userModel.getUsername(), userModel.getPassword());
    }


   /* @GetMapping("/login/oauth2")
    public String loginWithOAuth2(@RequestParam String code, @RequestParam String receivedState, HttpServletRequest request) {
        return userService.loginWithOAuth2(code, receivedState, request);
    }

    @GetMapping("/startOAuth2")
    public void startOAuth2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.startOAuth2(request, response);
    }*/

    /*@PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return userService.authenticate(username, password);
    }*/

    /*@GetMapping("/validate")
    public String validateToken(@RequestHeader("Authorization") String token) {
        return userService.validateToken(token) ? "Valid token" : "Invalid token";
    }*/
}

