package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.ApiService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final ApiService apiService;
    private final RestaurantService restaurantService;




    @GetMapping("/api")
    public ResponseEntity<List<RestaurantEntity>> getRestaurants() {
        List<RestaurantEntity> restaurants = apiService.getRestaurants();
        return ResponseEntity.ok(restaurants);
    }



}
