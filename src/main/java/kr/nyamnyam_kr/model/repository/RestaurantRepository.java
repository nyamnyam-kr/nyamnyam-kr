package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {

}
