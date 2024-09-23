package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;


public interface RestaurantService {


    List<RestaurantEntity> getCrawlingInfos();
    List<RestaurantModel> findAll();
    List<RestaurantModel> searchRestaurants(String keyword);
    RestaurantModel findById(Long id);
    Boolean existsById(Long id);
    ResponseEntity<RestaurantModel> getOneRestaurant(@PathVariable Long id);
    List<RestaurantModel> getRestaurantsByTag(List<String> tagNames);

}
