package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<AreaModel> countAreaList() {
        QRestaurantEntity restaurantEntity = QRestaurantEntity.restaurantEntity;

        List<Tuple> results = jpaQueryFactory.select(
                        Expressions.stringTemplate("regexp_substr({0}, '([^ ]+구)', 1, 1)", restaurantEntity.address).as("district"),
                        restaurantEntity.address.count()
                )
                .from(restaurantEntity)
                .groupBy(Expressions.stringTemplate("regexp_substr({0}, '([^ ]+구)', 1, 1)", restaurantEntity.address))
                .orderBy(restaurantEntity.address.count().desc())
                .limit(5)
                .fetch();

        return results.stream()
                .map(tuple -> {
                    AreaModel areaModel = new AreaModel();
                    areaModel.setArea(tuple.get(0, String.class));
                    areaModel.setTotal(tuple.get(1, Long.class));
                    return areaModel;
                })
                .collect(Collectors.toList());
    }



}
