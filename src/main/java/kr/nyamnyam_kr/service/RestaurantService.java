package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    RestaurantEntity save(RestaurantModel restaurantModel);

    List<RestaurantEntity> findAll();

    Optional<RestaurantEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
