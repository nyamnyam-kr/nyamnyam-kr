package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {
}
