package kr.nyamnyam_kr.service;

import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {


    List<RestaurantEntity> findAll();

    Optional<RestaurantEntity> findById(Long id);

    boolean deleteById(Long id);

    boolean existsById(Long id);

    long count();

}
