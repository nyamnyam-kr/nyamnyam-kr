package kr.nyamnyam.controller;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.ApiService;
import kr.nyamnyam.service.CrawlService;
import kr.nyamnyam.service.RestaurantService;
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
    private final ApiService apiService;
    private final RestaurantService restaurantService;
    private final CrawlService crawlService;



    @GetMapping("/list")
    public ResponseEntity<List<CrawlingInfo>> getCrawled() {
        List<CrawlingInfo> crawlingInfo = crawlService.getCrawlingInfos();
        return ResponseEntity.ok(crawlingInfo);
     }

    @GetMapping("/list/{pageNum}")
    public ResponseEntity<List<CrawlingInfo>> list(@PathVariable int pageNum) {
        int totalCount = crawlService.getTotalCount();
        Pagination pagination = new Pagination(pageNum, totalCount);

        List<CrawlingInfo> crawlingInfoList = crawlService.getCrawlingInfos(
                pagination.getStartRow(), pagination.getEndRow());

        return ResponseEntity.ok(crawlingInfoList);
    }


    @GetMapping("/crawling")
    public void crawlRestaurants() {
        CrawlingInfo crawlingInfo = new CrawlingInfo();
        System.out.println("이벤트 수신");
        crawlService.crawlAndSaveInfos();
    }


}
