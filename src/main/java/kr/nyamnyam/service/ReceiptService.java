package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.Chart.CheckModel;
import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.ReceiptEntity;

import java.util.List;

public interface ReceiptService {

    RestaurantModel save(ReceiptEntity receipt);

    List<TotalModel> showTotalCount();

    List<ReceiptEntity> findByUserId(Long id);

    Boolean deleteById(Long id);

    ReceiptEntity findById(Long id);

    ReceiptEntity show(ReceiptEntity receipt);


}