package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;



    @Override
    public List<RestaurantEntity> getCrawlingInfos() {
        return List.of();
    }

    @Override
    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<RestaurantEntity> searchRestaurants(String keyword) {
        return restaurantRepository.searchRestaurant(keyword);
    }

    @Override
    public RestaurantEntity findById(Long id) {
        if (existsById(id)) {
            return restaurantRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean existsById(Long id) {
        if(restaurantRepository.existsById(id)) {
            return true;
        }
        return false;
    }


   /* public ResponseEntity<RestaurantEntity> getOneRestaurant(@PathVariable Long id) {
        Optional<RestaurantEntity> restaurant = findById(id);

        if (restaurant.isPresent()) {
            return ResponseEntity.ok(restaurant.get()); // 200 OK 응답과 함께 RestaurantEntity 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found 응답
        }
    }*/


}
