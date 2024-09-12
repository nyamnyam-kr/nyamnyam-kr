package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {
   // boolean saveRestaurantFromApi(List<RestaurantEntity> restaurantList);
    void crawlAndSaveInfos();


    List<RestaurantEntity> getCrawlingInfos();

    int getTotalCount();
}
