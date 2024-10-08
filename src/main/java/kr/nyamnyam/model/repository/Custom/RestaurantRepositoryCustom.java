package kr.nyamnyam.model.repository.Custom;


import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;

import java.util.List;

public interface RestaurantRepositoryCustom {

    List<RestaurantEntity> searchRestaurant(String keyword);

    List<RestaurantEntity> findByTagName(List<String> tagNames);

    List<RestaurantEntity> findByCategoryUsingMenu(List<String> categories);

    List<AreaModel> countAreaList();

    RestaurantEntity randomRestaurant(String userId);

    // List<TotalModel> restaurantsByAge(String userId);



}