package kr.nyamnyam.controller;

import kr.nyamnyam.service.AdminService;
import kr.nyamnyam.service.OpinionService;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {


    private final UserService userService;
    private final AdminService adminService;
    private final OpinionService opinionService;

    @GetMapping("/reportAll")
    public ResponseEntity<List<?>> reportAll() {
        return ResponseEntity.ok(opinionService.findAll());
    }

    @GetMapping("/upvote")
    public ResponseEntity<List<?>> upvote() {
        return ResponseEntity.ok(adminService.postUpvote());
    }


    @GetMapping("/countUserList")
    public ResponseEntity<List<?>> countUserList() {
        return ResponseEntity.ok(adminService.countUserList());
    }

    // 레스토랑이 많이 등록된 지역 list
    @GetMapping("/countAreaList")
    public ResponseEntity<List<?>> countAreaList() {
        return ResponseEntity.ok(adminService.countAreaList());
    }


    // 포스트가 가장 많은 음식점 list
    @GetMapping("/countPostList")
    public ResponseEntity<List<?>> countPostList() {
        return ResponseEntity.ok(adminService.countPostList());
    }




}
