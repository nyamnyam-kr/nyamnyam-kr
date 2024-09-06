package kr.nyamnyam_kr.model.repository;

import jakarta.persistence.EntityManager;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {

}
