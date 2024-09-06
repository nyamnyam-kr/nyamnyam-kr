package kr.nyamnyam_kr.model.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomRestaurantRepositoryImpl implements CustomRestaurantRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RestaurantEntity> findByName(String name) {
        return List.of();
    }
}
