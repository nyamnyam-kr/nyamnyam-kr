package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
