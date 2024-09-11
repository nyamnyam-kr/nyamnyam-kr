package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    RestaurantEntity save(RestaurantModel restaurantModel);

    List<RestaurantEntity> findAll();

    RestaurantEntity findById(Long id);

    Boolean deleteById(Long id);

    boolean existsById(Long id);

    Long count();

    List<RestaurantEntity> findAllPage(int page, int pageSize);

    List<RestaurantEntity> findAllUserName(String username);

    void getNew();

    void crawlingBot();
}
