package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl {
    private final RestaurantRepository restaurantRepository;


/*    @Override
    public boolean saveRestaurantFromApi(List<RestaurantEntity> restaurant) {
        try {
            restaurantRepository.saveAll(restaurant);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/




}
