package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.CrawlService;
import kr.nyamnyam.service.RestaurantService;
import kr.nyamnyam.service.impl.CrawlServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CrawlService crawlService;


    @GetMapping("/list")
    public ResponseEntity<List<RestaurantEntity>> getCrawled() {
        List<RestaurantEntity> restaurants = restaurantService.getCrawlingInfos();
        return ResponseEntity.ok(restaurants);
     }



    @GetMapping("/crawling")
    public void crawlRestaurants() {
        RestaurantEntity restaurant = new RestaurantEntity();
        System.out.println("이벤트 수신");
        crawlService.crawlAndSaveInfos();
    }

    @GetMapping("/restaurants")
    public List<RestaurantEntity> getRestaurants() {
        return restaurantService.findAll();
    }


}
