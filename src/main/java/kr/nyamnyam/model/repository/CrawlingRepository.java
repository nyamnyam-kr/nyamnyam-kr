package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.model.entity.RestaurantEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CrawlingRepository extends JpaRepository<CrawlingInfo, Long> {

    @Query("SELECT c FROM CrawlingInfo c ORDER BY c.id ASC")
    List<CrawlingInfo> findCrawlingInRange(@Param("startRow") int startRow, @Param("endRow") int endRow);

}
