package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.RestaurantEntity;


import java.util.List;


public interface RestaurantService {


    List<RestaurantEntity> getCrawlingInfos();
    List<RestaurantEntity> findAll();

}
