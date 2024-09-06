package kr.nyamnyam_kr.controller;

import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;


    @GetMapping("findAll")
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(restaurantService.findAll());
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
