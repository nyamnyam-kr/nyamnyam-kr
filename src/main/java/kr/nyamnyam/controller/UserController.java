package kr.nyamnyam.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Boolean> existsById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.existsById(id));
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<UsersEntity>> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UsersEntity>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(userService.count());
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<UsersEntity> update(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.update(userModel));
    }

    @PostMapping("/join")
    public ResponseEntity<UsersEntity> join(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.save(userModel));
    }

    @GetMapping("/login/oauth2")
    public ResponseEntity<String> loginWithOAuth2(
            @RequestParam String code,
            @RequestParam String receivedState,
            HttpServletRequest request) {
        return ResponseEntity.ok(userService.loginWithOAuth2(code, receivedState, request));
    }

    @GetMapping("/startOAuth2")
    public void startOAuth2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.startOAuth2(request, response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.authenticate(username, password));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        return userService.validateToken(token)
                ? ResponseEntity.ok("Valid token")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}

