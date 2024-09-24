package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<RestaurantEntity> findByName(String name);

    List<RestaurantEntity> searchRestaurant(String keyword);

    List<RestaurantEntity> findByTagName(List<String> tagNames);

    List<RestaurantEntity> findByCategoryUsingMenu(List<String> categories);

    List<AreaModel> countAreaList();

}
