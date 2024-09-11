package kr.nyamnyam.service;


import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface ApiService {
    List<RestaurantEntity> getRestaurants();
}
