package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.RestImgService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestImgService restImgService;

    @Override
    public boolean saveRestaurantFromApi(List<RestaurantEntity> restaurant) {
        try {
            restaurantRepository.saveAll(restaurant);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existsByNameAndAddress(String name, String address) {
        try {
            restaurantRepository.existsByNameAndAddress(name, address);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

/*    @Override
    public void updateRestaurantWithImage(RestaurantEntity restaurant) {
        String imageUrl = restImgService.extractImageUrl(restaurant.getPostUrl());

        restaurant.setImageUrl(imageUrl);
        restaurantRepository.save(restaurant);
    }*/
}
