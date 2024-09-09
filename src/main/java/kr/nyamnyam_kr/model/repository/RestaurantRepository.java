package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {
    @Modifying
    @Query("delete from RestaurantEntity r where r.id in :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);

}
