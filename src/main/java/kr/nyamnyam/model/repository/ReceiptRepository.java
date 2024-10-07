package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ReceiptEntity;
import kr.nyamnyam.model.repository.Custom.ReceiptRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long>, ReceiptRepositoryCustom {

    ReceiptEntity findByDate(String date);

    List<ReceiptEntity> findByUserId(String id);

}
