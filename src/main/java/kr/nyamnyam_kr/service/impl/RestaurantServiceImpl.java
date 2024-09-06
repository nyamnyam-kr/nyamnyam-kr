package kr.nyamnyam_kr.service.impl;

import kr.nyamnyam_kr.model.domain.RestaurantModel;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.model.repository.RestaurantRepository;
import kr.nyamnyam_kr.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantEntity save(RestaurantModel restaurantModel) {
        return restaurantRepository.save(RestaurantEntity.toRestaurantEntity(restaurantModel));
    }

    @Override
    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<RestaurantEntity> findById(Long id) {
        return restaurantRepository.findById(id);
    }


    @Override
    public boolean deleteById(Long id) {
        try {
            if (restaurantRepository.existsById(id)) {
                restaurantRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    @Override
    public long count() {
        return restaurantRepository.count();
    }


}
