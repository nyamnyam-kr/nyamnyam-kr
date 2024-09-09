package kr.nyamnyam_kr.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        RestaurantEntity restaurantEntity = RestaurantEntity.toRestaurantEntity(restaurantModel);
        return restaurantRepository.save(restaurantEntity);
    }

    @Override
    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public RestaurantEntity findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id " + id));
    }


    @Override
    public Boolean deleteById(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void deleteAll(List<Long> restaurantsIds) {
        restaurantRepository.deleteAllByIds(restaurantsIds);
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
