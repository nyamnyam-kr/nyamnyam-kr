package kr.nyamnyam_kr.model.repository.Custom;

import kr.nyamnyam_kr.model.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<RestaurantEntity> findByName(String name);
}
