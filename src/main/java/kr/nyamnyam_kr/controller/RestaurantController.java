package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.ReplyModel;
import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.ReplyEntity;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/restaurant/")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("save")
    public ResponseEntity<RestaurantEntity> save(@RequestBody RestaurantModel restaurantModel) {
        System.out.println("restaurantModel = " + restaurantModel);
        return ResponseEntity.ok(restaurantService.save(restaurantModel));
    }

    @GetMapping("findAll")
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("findById")
    public ResponseEntity<RestaurantEntity> findById(@RequestParam Long id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    @GetMapping("deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(restaurantService.deleteById(id));
    }

    @GetMapping("existsById")
    public boolean existsById(Long id) {
        return restaurantService.existsById(id);
    }

    @GetMapping("count")
    public long count() {
        return restaurantService.count();
    }
}
