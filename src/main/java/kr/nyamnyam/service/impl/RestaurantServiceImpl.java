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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;



    @Override
    public List<RestaurantEntity> getCrawlingInfos() {
        return List.of();
    }

    @Override
    public List<RestaurantModel> findAll() {
        List<RestaurantEntity> entities = restaurantRepository.findAll();
        return entities.stream()
                .map(RestaurantModel::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<RestaurantModel> searchRestaurants(String keyword) {
        List<RestaurantEntity> entities = restaurantRepository.searchRestaurant(keyword);
        return entities.stream()
                .map(RestaurantModel::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantModel findById(Long id) {
        Optional<RestaurantEntity> entity = restaurantRepository.findById(id);
        return entity.map(RestaurantModel::toDto).orElse(null);
    }


    @Override
    public Boolean existsById(Long id) {
        if(restaurantRepository.existsById(id)) {
            return true;
        }
        return false;
    }


    @Override
    public ResponseEntity<RestaurantModel> getOneRestaurant(@PathVariable Long id) {
        Optional<RestaurantEntity> restaurantEntityOpt = restaurantRepository.findById(id);
        if (restaurantEntityOpt.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOpt.get();
            RestaurantModel restaurantModel = RestaurantModel.toDto(restaurantEntity);

            return ResponseEntity.ok(restaurantModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
