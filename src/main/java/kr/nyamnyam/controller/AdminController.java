package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.AdminService;
import kr.nyamnyam.service.OpinionService;
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

//    // user 연령별 추천
//    @GetMapping("/recommendByAge/{id}")
//    public ResponseEntity<List<?>> recommendByAge(@RequestHeader String id) {
//        return ResponseEntity.ok(adminService.recommendByAge(id));
//    }

    @GetMapping("/randomByUserId/{id}")
    public ResponseEntity<RestaurantEntity> randomByUserId(@PathVariable String id) {
        return ResponseEntity.ok(adminService.randomRestaurantByUserId(id));
    }

    @GetMapping("/receiptCount")
    public ResponseEntity<List<CostModel>> receiptCount() {
        return ResponseEntity.ok(adminService.receiptRestaurant());
    }




}
