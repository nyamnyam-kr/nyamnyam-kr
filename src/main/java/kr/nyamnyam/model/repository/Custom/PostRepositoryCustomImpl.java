package kr.nyamnyam.model.repository.Custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.nyamnyam.model.entity.QPostEntity.postEntity;
import static kr.nyamnyam.model.entity.QRestaurantEntity.restaurantEntity;
import static kr.nyamnyam.model.entity.QUpvoteEntity.upvoteEntity;
import static kr.nyamnyam.model.entity.QUserEntity.userEntity;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> postUpvote() {
        return jpaQueryFactory.select(postEntity.content)
                .from(upvoteEntity)
                .join(postEntity).on(postEntity.id.eq(upvoteEntity.post.id))
                .groupBy(upvoteEntity.post.id)
                .orderBy(upvoteEntity.post.id.asc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<String> findNicknameFromUpvote(){
        return jpaQueryFactory.select(userEntity.nickname)
                .from(upvoteEntity)
                .join(postEntity).on(postEntity.id.eq(upvoteEntity.post.id))
                .join(userEntity).on(postEntity.userId.eq(userEntity.id))
                .groupBy(upvoteEntity.post.id)
                .orderBy(upvoteEntity.post.id.asc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<String> findRestaurantFromUpvote() {
        return jpaQueryFactory.select(restaurantEntity.name)
                .from(upvoteEntity)
                .join(postEntity).on(postEntity.id.eq(upvoteEntity.post.id))
                .join(restaurantEntity).on(restaurantEntity.postId.eq(postEntity.id))
                .groupBy(upvoteEntity.post.id)
                .orderBy(upvoteEntity.post.id.asc())
                .limit(5)
                .fetch();

    }









}
