package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<RestaurantEntity> findByName(String name);
}
