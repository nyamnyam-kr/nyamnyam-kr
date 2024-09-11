package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.ApiService;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/restaurant/")
public class RestaurantController {
    private final ApiService apiService;
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


    @GetMapping("api")
    public ResponseEntity<List<RestaurantEntity>> getRestaurants() {
        List<RestaurantEntity> restaurants = apiService.getRestaurants();
        return ResponseEntity.ok(restaurants);

    @GetMapping("findPage/{pageNo}")
    public ResponseEntity<?> findPage(@PathVariable int pageNo, @RequestParam int pageSize) {
        // 특정 페이지의 리스트를 보여주는 메소드 호출
        System.out.println("pageNo = " + pageNo);
        return ResponseEntity.ok(restaurantService.findAllPage(pageNo, pageSize));

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

    @GetMapping("getNews")
    public void getNews() {
        restaurantService.getNew();
    }

    @GetMapping("crawlingBot")
    public void crawlingBot() {
        System.out.println("RestaurantController.crawlingBot");
        restaurantService.crawlingBot();
    }


}
