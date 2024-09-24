package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.CountModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.QPostEntity;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import kr.nyamnyam.model.entity.QUpvoteEntity;
import kr.nyamnyam.model.entity.QUsersEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // post에 nickname 불러오기
    @Override
    public List<Tuple> findAllByRestaurantWithNickname(Long restaurantId) {
        QPostEntity postEntity = QPostEntity.postEntity;
        QUsersEntity usersEntity = QUsersEntity.usersEntity;

        return jpaQueryFactory
                .select(postEntity, usersEntity.nickname)
                .from(postEntity)
                .join(usersEntity).on(postEntity.userId.eq(usersEntity.id))
                .where(postEntity.restaurant.id.eq(restaurantId))
                .fetch();
    }

    // 가장 많은 post 쓴 사람 순위 뽑아내기
    @Override
    public List<CountModel> findNicknamesWithCounts() {
        QPostEntity postEntity = QPostEntity.postEntity;
        QUsersEntity usersEntity = QUsersEntity.usersEntity;

        JPAQuery<Tuple> query = jpaQueryFactory
                .select(usersEntity.nickname, postEntity.count())
                .from(postEntity)
                .join(usersEntity).on(postEntity.userId.eq(usersEntity.id)) // 수정: 올바른 조인 조건
                .groupBy(usersEntity.nickname)
                .orderBy(postEntity.count().desc());

        List<Tuple> result = query.fetch();


        return result.stream()
                .map(tuple -> new CountModel(
                        tuple.get(usersEntity.nickname),
                        tuple.get(postEntity.count())))
                .collect(Collectors.toList());
    }


    // 가장 많은 추천을 받은 postlist
    @Override
    public List<String> postUpvote() {

        QPostEntity postEntity = QPostEntity.postEntity;
        QUpvoteEntity upvoteEntity = QUpvoteEntity.upvoteEntity;

        return jpaQueryFactory.select(postEntity.content)
                .from(upvoteEntity)
                .join(postEntity).on(postEntity.id.eq(upvoteEntity.postId))
                .groupBy(upvoteEntity.postId)
                .orderBy(upvoteEntity.postId.asc())
                .limit(5)
                .fetch();
    }

    // 가장 많은 추천을 받은 post의 nickname list
    @Override
    public List<String> findNicknameFromUpvote() {

        QPostEntity postEntity = QPostEntity.postEntity;
        QUpvoteEntity upvoteEntity = QUpvoteEntity.upvoteEntity;
        QUsersEntity usersEntity = QUsersEntity.usersEntity;

        return jpaQueryFactory.select(usersEntity.nickname)
                .from(upvoteEntity)
                .join(postEntity).on(postEntity.id.eq(upvoteEntity.postId))
                .join(usersEntity).on(postEntity.userId.eq(usersEntity.id))
                .groupBy(upvoteEntity.postId)
                .orderBy(upvoteEntity.postId.asc())
                .limit(5)
                .fetch();
    }


    // 가장 많은 추천을 받은 음식점 list
    @Override
    public List<String> findRestaurantFromUpvote() {

        QPostEntity postEntity = QPostEntity.postEntity;
        QUpvoteEntity upvoteEntity = QUpvoteEntity.upvoteEntity;
        QRestaurantEntity restaurantEntity = QRestaurantEntity.restaurantEntity;

        return jpaQueryFactory.select(restaurantEntity.name)
                .from(upvoteEntity)
                .join(postEntity).on(postEntity.id.eq(upvoteEntity.postId))
                .join(restaurantEntity).on(restaurantEntity.id.eq(postEntity.id))
                .groupBy(upvoteEntity.postId)
                .orderBy(upvoteEntity.postId.asc())
                .limit(5)
                .fetch();

    }

    @Override
    public List<TotalModel> countRestaurantList() {
        QPostEntity postEntity = QPostEntity.postEntity;
        QRestaurantEntity restaurantEntity = QRestaurantEntity.restaurantEntity;

        List<Tuple> results = jpaQueryFactory.select(
                        restaurantEntity.name,
                        postEntity.restaurant.id.count()
                )
                .from(restaurantEntity)
                .leftJoin(postEntity)
                .on(restaurantEntity.id.eq(postEntity.restaurant.id))
                .groupBy(restaurantEntity.name)
                .orderBy(postEntity.restaurant.id.count().desc())
                .limit(5)
                .fetch();

        return results.stream()
                .map(tuple -> {
                    TotalModel totalModel = new TotalModel();
                    totalModel.setRestaurantName(tuple.get(restaurantEntity.name));
                    totalModel.setTotal(tuple.get(postEntity.restaurant.id.count()));
                    return totalModel;
                })
                .collect(Collectors.toList());
    }



}
