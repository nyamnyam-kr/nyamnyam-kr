package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    boolean saveRestaurantFromApi(List<RestaurantEntity> restaurantList);


    boolean existsByNameAndAddress(String name, String address);

  /*  void updateRestaurantWithImage(RestaurantEntity restaurant);*/

    List<RestaurantEntity> findAllPage(int page, int pageSize);

    void getNew();

    Long count();

    void crawlingBot();
}
