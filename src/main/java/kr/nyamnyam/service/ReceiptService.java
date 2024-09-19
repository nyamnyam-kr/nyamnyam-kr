package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.ReceiptModel;
import kr.nyamnyam.model.entity.ReceiptEntity;

public interface ReceiptService {

    Boolean save(ReceiptEntity receipt);

}
