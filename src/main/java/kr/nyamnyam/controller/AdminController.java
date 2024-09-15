package kr.nyamnyam.controller;

import kr.nyamnyam.service.AdminService;
import kr.nyamnyam.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {


    private final UserService userService;
    private final AdminService adminService;




    @GetMapping("/upvote")
    public ResponseEntity<List<?>> upvote() {
        return ResponseEntity.ok(adminService.postUpvote());
    }

    @GetMapping("/count")
    public ResponseEntity<List<?>> count() {
        return ResponseEntity.ok(adminService.countList());
    }




}
