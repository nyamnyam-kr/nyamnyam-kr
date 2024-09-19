package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.RestaurantRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> , RestaurantRepositoryCustom {


    @Query("SELECT r.name FROM RestaurantEntity r")
    List<String> findAllNames();
}
