package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ReceiptModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.ReceiptEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.ReceiptRepository;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantModel save(ReceiptEntity receipt) {
        ReceiptEntity save = repository.save(receipt);
        String restaurantName = save.getName();
        Long restaurantId = repository.findRestaurantId(restaurantName);
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(restaurantId);
        return restaurantEntity.map(RestaurantModel::toDto).orElse(null);
    }

}
