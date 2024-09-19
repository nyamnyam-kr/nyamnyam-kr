package kr.nyamnyam.controller;

import kr.nyamnyam.model.domain.ReplyModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.ReplyEntity;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.CrawlService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CrawlService crawlService;

/*
    @GetMapping("/list")
    public ResponseEntity<List<RestaurantEntity>> getCrawled() {
        List<RestaurantEntity> restaurants = restaurantService.getCrawlingInfos();
        return ResponseEntity.ok(restaurants);
     }*/


    // 크롤링이벤트 발생 api
    @GetMapping("/crawling")
    public void crawlRestaurants() {
        RestaurantEntity restaurant = new RestaurantEntity();
        System.out.println("이벤트 수신");
        crawlService.crawlAndSaveInfos();
    }

    // 레스토랑 목록 불러오는 api
    @GetMapping("/restaurants")
    public List<RestaurantModel> getRestaurants() {
        return restaurantService.findAll();
    }

    // 맛집 검색(이름, 유형, 메뉴)
    @GetMapping("/search")
    public List<RestaurantModel> searchRestaurants(@RequestParam("q") String query) {
        return restaurantService.searchRestaurants(query);
    }

    // 맛집 상세보기
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantModel> getRestaurant(@PathVariable Long id) {
        ResponseEntity<RestaurantModel> restaurantOpt = restaurantService.getOneRestaurant(id);
        return restaurantOpt;
    }
}
