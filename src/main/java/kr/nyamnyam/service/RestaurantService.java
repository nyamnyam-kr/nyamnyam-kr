package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;


public interface RestaurantService {


    List<RestaurantEntity> getCrawlingInfos();
    List<RestaurantEntity> findAll();
    List<RestaurantEntity> searchRestaurants(String keyword);
    RestaurantEntity findById(Long id);
    Boolean existsById(Long id);
    //ResponseEntity<RestaurantEntity> getOneRestaurant(@PathVariable Long id);

}
