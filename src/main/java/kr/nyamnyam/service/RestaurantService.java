package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {
    boolean saveRestaurantFromApi(List<RestaurantEntity> restaurantList);

    boolean existsByNameAndAddress(String name, String address);


}
