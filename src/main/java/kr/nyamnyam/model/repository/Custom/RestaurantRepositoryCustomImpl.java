package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.*;
import kr.nyamnyam.model.domain.Chart.AreaModel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;


import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
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

    // 랜덤레스토랑
    @Override
    public RestaurantEntity randomRestaurant(Long userId) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;
        QPostEntity post = QPostEntity.postEntity;

         String type = jpaQueryFactory
                 .select(restaurant.type)
                 .from(post)
                 .join(restaurant).on(restaurant.id.eq(post.restaurant.id))
                 .where(post.userId.eq(userId))
                 .groupBy(restaurant.type)
                 .orderBy(post.count().desc())
                 .limit(1)
                 .fetchOne();

        RestaurantEntity randomRestaurant = jpaQueryFactory
                .selectFrom(restaurant)
                .where(restaurant.type.eq(type))
                .orderBy(numberTemplate(Double.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();

        return randomRestaurant;

    }

    @Override
    public List<RestaurantEntity> restaurantsByGender(String gender) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;
        QPostEntity post = QPostEntity.postEntity;
        QUsersEntity user = QUsersEntity.usersEntity;

        JPAQuery<String> subQuery = new JPAQuery<>();

        String topType = subQuery.select(restaurant.type)
                .from(restaurant)
                .join(post).on(post.restaurant.id.eq(restaurant.id))
                .join(user).on(user.id.eq(post.userId))
                .where(user.gender.eq(gender))
                .groupBy(restaurant.type)
                .orderBy(restaurant.type.count().desc())
                .limit(1)
                .fetchOne();

        if (topType == null) {
            return Collections.emptyList();
        }

        return jpaQueryFactory
                .select(restaurant)
                .from(restaurant)
                .where(restaurant.type.eq(topType))
                .fetch();

    }




    @Override
    public List<TotalModel> restaurantsByAge(Long userId) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;
        QPostEntity post = QPostEntity.postEntity;
        QUsersEntity user = QUsersEntity.usersEntity;

        Long age = jpaQueryFactory
                .select(user.age)
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();

        List<Tuple> results = null;

        if (age != null) {
            // 10대, 20대, 30대 또는 40대에 따라 쿼리 실행
            results = jpaQueryFactory
                    .select(restaurant.name, post.restaurant.id.count())
                    .from(restaurant)
                    .join(post).on(post.restaurant.id.eq(restaurant.id))
                    .join(user).on(user.id.eq(post.userId))
                    .where(user.age.between(age < 20 ? 10 : (age < 30 ? 20 : (age < 40 ? 30 : 40)),
                            age < 20 ? 19 : (age < 30 ? 29 : (age < 40 ? 39 : 49)))) // 나이에 따라 범위 설정
                    .groupBy(restaurant.id)
                    .orderBy(post.restaurant.id.count().desc())
                    .limit(5)
                    .fetch();
        }

        return  results.stream()
                .map(tuple -> {
                    TotalModel totalModel = new TotalModel();
                    totalModel.setRestaurantName(tuple.get(restaurant.name));
                    totalModel.setTotal(tuple.get(post.restaurant.id.count()));
                    return totalModel;
                })
                .collect(Collectors.toList());

    }


    @Override
    public List<RestaurantEntity> findByTagName(List<String> tagNames) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;
        QPostEntity post = QPostEntity.postEntity;
        QPostTagEntity postTag = QPostTagEntity.postTagEntity;

        long tagCount = tagNames.size();

        return jpaQueryFactory
                .selectFrom(restaurant)
                .distinct()
                .join(restaurant.posts, post)
                .join(post.postTags, postTag)
                .where(postTag.tag.name.in(tagNames))
                .groupBy(restaurant.id)
                .having(numberTemplate(Long.class, "COUNT(DISTINCT {0})", postTag.tag.name).eq(tagCount))
                .fetch();
    }



    @Override
    public List<RestaurantEntity> findByCategoryUsingMenu(List<String> categories) {
        QRestaurantEntity restaurant = QRestaurantEntity.restaurantEntity;

        BooleanExpression categoryExpression = categories.stream()
                .map(category -> getCategoryCondition(restaurant.type, category))
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::or)
                .orElse(null);

        return jpaQueryFactory
                .select(restaurant)
                .distinct()
                .from(restaurant)
                .where(categoryExpression)
                .fetch();
    }

    private BooleanExpression getCategoryCondition(StringExpression type, String category) {
        if (category.equals("한식")) {
            return type.contains("육류")
                    .or(type.contains("고기"))
                    .or(type.contains("한식"))
                    .or(type.contains("칼국수"))
                    .or(type.contains("만두"))
                    .or(type.contains("곱창"))
                    .or(type.contains("막창"))
                    .or(type.contains("감자"))
                    .or(type.contains("돼지"))
                    .or(type.contains("냉면"))
                    .or(type.contains("쭈꾸미"));
        } else if (category.equals("일식")) {
            return type.contains("일식")
                    .or(type.contains("초밥"))
                    .or(type.contains("롤"))
                    .or(type.contains("이자카야"))
                    .or(type.contains("덮밥"));

        } else if (category.equals("중식")) {
            return type.contains("중식")
                    .or(type.contains("양꼬치"))
                    .or(type.contains("마라탕"))
                    .or(type.contains("짜장"));
        } else if (category.equals("양식")) {
            return type.contains("양식")
                    .or(type.contains("이탈리아"))
                    .or(type.contains("스테이크"))
                    .or(type.contains("립"))
                    .or(type.contains("레스토랑"))
                    .or(type.contains("파스타"))
                    .or(type.contains("스테이크"))
                    .or(type.contains("돈까스"));
        } else if (category.equals("아시아")) {
            return type.contains("아시아")
                    .or(type.contains("베트남"))
                    .or(type.contains("태국"))
                    .or(type.contains("쌀국수"))
                    .or(type.contains("팟타이"));
        } else if (category.equals("해산물")) {
            return type.contains("생선")
                    .or(type.contains("회"))
                    .or(type.contains("킹크랩"));
        } else if (category.equals("술집")) {
            return type.contains("술")
                    .or(type.contains("주점"))
                    .or(type.contains("이자카야"))
                    .or(type.contains("바"))
                    .or(type.contains("와인"))
                    .or(type.contains("맥주"))
                    .or(type.contains("호프"));
        } else if (category.equals("카페")) {
            return type.contains("카페")
                    .or(type.contains("베이커리"))
                    .or(type.contains("커피"));
        } else
            return null;
    }

}
