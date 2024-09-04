package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.ReplyModel;
import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.ReplyEntity;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.model.entity.ZoneEntity;
import kr.nyamnyam_kr.service.RestaurantService;
import kr.nyamnyam_kr.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final ZoneService zoneService;


    @PostMapping("/save")
    public ResponseEntity<RestaurantEntity> save(@RequestBody RestaurantModel restaurantModel) {
        return ResponseEntity.ok(restaurantService.save(restaurantModel));
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.deleteById(id));
    }

    @GetMapping("/existsById")
    public boolean existsById(Long id) {
        return restaurantService.existsById(id);
    }




}
