package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<RestaurantEntity> searchRestaurant(String keyword);
}
