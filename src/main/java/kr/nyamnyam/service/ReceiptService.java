package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.ReceiptEntity;

public interface ReceiptService {

    RestaurantModel save(ReceiptEntity receipt);

}
