package kr.nyamnyam_kr.model.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import kr.nyamnyam_kr.model.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomRestaurantRepository {
    List<RestaurantEntity> findByName(String name);
}

