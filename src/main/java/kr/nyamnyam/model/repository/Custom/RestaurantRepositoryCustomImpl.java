package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.nyamnyam.model.entity.QRestaurantEntity.restaurantEntity;

@RequiredArgsConstructor
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RestaurantEntity> findByName(String name) {
        return jpaQueryFactory.selectFrom(restaurantEntity)
                .where(restaurantEntity.name.eq(name))
                .fetch();
    }

    @Override
    public List<RestaurantEntity> searchRestaurant(String keyword) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;

        BooleanExpression nameContains = restaurant.name.containsIgnoreCase(keyword);
        BooleanExpression addressContains = restaurant.address.containsIgnoreCase(keyword);
        BooleanExpression typeContains = restaurant.type.containsIgnoreCase(keyword);
        BooleanExpression menuContains = restaurant.menu.containsIgnoreCase(keyword);

        return jpaQueryFactory.selectFrom(restaurant)
                .where(nameContains.or(addressContains).or(typeContains).or(menuContains))
                .fetch();

    }
}
