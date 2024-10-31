package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.AdminService;
import kr.nyamnyam.service.OpinionService;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;
    private final OpinionService opinionService;

    @GetMapping("/reportAll")
    public ResponseEntity<List<?>> reportAll() {
        return ResponseEntity.ok(opinionService.findAll());
    }

    @GetMapping("/countUserList")
    public ResponseEntity<List<?>> countUserList() {
        return ResponseEntity.ok(adminService.countUserList());
    }

    @GetMapping("/countAreaList")
    public ResponseEntity<List<?>> countAreaList() {
        return ResponseEntity.ok(adminService.countAreaList());
    }


    @GetMapping("/countPostList")
    public ResponseEntity<List<?>> countPostList() {
        return ResponseEntity.ok(adminService.countPostList());
    }

    @GetMapping("/typeList/{id}")
    public ResponseEntity<List<?>> typeList(@PathVariable String id) {
        return ResponseEntity.ok(adminService.typeList(id));
    }

    @GetMapping("/randomByUserId/{id}")
    public ResponseEntity<RestaurantEntity> randomByUserId(@PathVariable String id) {
        return ResponseEntity.ok(adminService.randomRestaurantByUserId(id));
    }

    @GetMapping("/receiptCount")
    public ResponseEntity<List<CostModel>> receiptCount() {
        return ResponseEntity.ok(adminService.receiptRestaurant());
    }

    @GetMapping("/upvoteRestaurant")
    public ResponseEntity<List<?>> upvoteRestaurant() {
        return ResponseEntity.ok(adminService.findRestaurantFromUpvote());
    }

    @GetMapping("userAreaList/{id}")
    public ResponseEntity<List<?>> userAreaList(@PathVariable String id) {
        return ResponseEntity.ok(adminService.userAreaList(id));
    }

    @GetMapping("/todayPost")
    public ResponseEntity<List<?>> todayList() {
        return ResponseEntity.ok(adminService.findPostsByToday());
    }

//    @PostMapping("/{postId}/disabled")
//    public ResponseEntity<Boolean> postUnacceptable(@PathVariable Long postId) {
//        return ResponseEntity.ok(adminService.postUpdateDisabled(postId));
//    }

/*    @PostMapping("/{postId}/able")
    public ResponseEntity<Boolean> postAble(@PathVariable Long postId) {
        return ResponseEntity.ok(adminService.postAble(postId));
    }*/



}
