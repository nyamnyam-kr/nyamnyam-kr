package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {
    boolean existsByNameAndAddress(String name,String address);
}
