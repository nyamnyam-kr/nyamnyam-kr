package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ReceiptModel;
import kr.nyamnyam.model.entity.ReceiptEntity;
import kr.nyamnyam.model.repository.ReceiptRepository;
import kr.nyamnyam.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository repository;

    @Override
    public Boolean save(ReceiptEntity receipt) {
        repository.save(receipt);
        return true;
    }
}
