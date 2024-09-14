package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
