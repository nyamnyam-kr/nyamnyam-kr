package kr.nyamnyam_kr.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.nyamnyam_kr.model.entity.QRestaurantEntity;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.model.repository.querydsl.CustomRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long>, CustomRestaurantRepository {

}

