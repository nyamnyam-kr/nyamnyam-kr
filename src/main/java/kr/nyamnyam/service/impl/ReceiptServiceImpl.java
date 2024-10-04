package kr.nyamnyam.service.impl;

import jakarta.transaction.Transactional;
import kr.nyamnyam.config.RestTemplateConfig;
import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.ReceiptEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.ReceiptRepository;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final RestTemplateConfig restTemplateConfig;

    @Override
    public RestaurantModel save(ReceiptEntity receipt) {
        ReceiptEntity existingReceipt = repository.findByDate(receipt.getDate());

        if (existingReceipt != null && existingReceipt.getName().equals(receipt.getName())) {
            return null;
        }
        ReceiptEntity savedReceipt = repository.save(receipt);
        String restaurantName = savedReceipt.getName();
        System.out.println(restaurantName);

        Long restaurantId = repository.findRestaurantId(restaurantName);
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(restaurantId);

        System.out.println(restaurantEntity);

        return restaurantEntity.map(RestaurantModel::toDto).orElse(null);
    }

    public ReceiptEntity show(ReceiptEntity receipt) {
        ReceiptEntity existingReceipt = repository.findByDate(receipt.getDate());

        if (existingReceipt != null && existingReceipt.getName().equals(receipt.getName())) {
            return existingReceipt;
        } else return null;
    }


    @Override
    @Transactional
    public List<CostModel> costModelList(Long userId) {
        return repository.costList(userId);
    }

    @Override
    public List<ReceiptEntity> findByUserId(Long id) {
        return repository.findByUserId(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public ReceiptEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }


}