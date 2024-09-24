package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.AreaModel;
import kr.nyamnyam.model.entity.QPostEntity;
import kr.nyamnyam.model.entity.QPostTagEntity;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;


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





    @Override
    public List<RestaurantEntity> findByTagName(List<String> tagNames) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;
        QPostEntity post = QPostEntity.postEntity;
        QPostTagEntity postTag = QPostTagEntity.postTagEntity;

        return jpaQueryFactory
                .selectFrom(restaurant)
                .distinct()
                .join(restaurant.posts, post)
                .join(post.postTags, postTag)
                .where(postTag.tag.name.in(tagNames))
                .fetch();
    }



    @Override
    public List<RestaurantEntity> findByCategoryUsingMenu(List<String> categories) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;

        BooleanExpression categoryExpression = categories.stream()
                .map(category -> getCategoryCondition(restaurant.menu, category))
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::or)
                .orElse(null);

        return jpaQueryFactory
                .select(restaurant)
                .from(restaurant)
                .where(categoryExpression)
                .fetch();
    }

    private BooleanExpression getCategoryCondition(StringExpression menu, String category) {
        if (category.equals("중식")) {
            return menu.contains("짜장")
                    .or(menu.contains("짬뽕"))
                    .or(menu.contains("탕수육"))
                    .or(menu.contains("중국"));
        } else if (category.equals("일식")) {
            return menu.contains("라멘")
                    .or(menu.contains("스시"))
                    .or(menu.contains("우동"))
                    .or(menu.contains("일본"));
        } else if (category.equals("한식")) {
            return menu.contains("김치")
                    .or(menu.contains("비빔밥"))
                    .or(menu.contains("불고기"))
                    .or(menu.contains("샤브"))
                    .or(menu.contains("국수"))
                    .or(menu.contains("만두"))
                    .or(menu.contains("냉면"))
                    .or(menu.contains("갈비"));
        } else if (category.equals("분식")) {
            return menu.contains("떡볶이")
                    .or(menu.contains("순대"))
                    .or(menu.contains("튀김"));
        } else if (category.equals("경양식")) {
            return menu.contains("돈까스")
                    .or(menu.contains("스테이크"))
                    .or(menu.contains("햄버거"));
        } else if (category.equals("양식")) {
            return menu.contains("파스타")
                    .or(menu.contains("피자"))
                    .or(menu.contains("이탈리아"))
                    .or(menu.contains("스파게티"));
        } else if (category.equals("카페")) {
            return menu.contains("아메리카노")
                    .or(menu.contains("카푸치노"))
                    .or(menu.contains("라떼"));
        } else if (category.equals("디저트")) {
            return menu.contains("케이크")
                    .or(menu.contains("빙수"))
                    .or(menu.contains("마카롱"));
        }
        return null;
    }

}
