package kr.nyamnyam.model.repository.Custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import kr.nyamnyam.model.entity.QWishListRestaurantEntity;
import kr.nyamnyam.model.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class WishListRestaurantCustomRepositoryImpl implements WishListRestaurantCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RestaurantEntity> findRestaurantsByUserIdAndWishListId(Long userId, Long wishListId) {
        QWishListRestaurantEntity qWishListRestaurantEntity = QWishListRestaurantEntity.wishListRestaurantEntity;
        QRestaurantEntity qRestaurantEntity = QRestaurantEntity.restaurantEntity;

        return jpaQueryFactory.selectFrom(qRestaurantEntity)
                .innerJoin(qWishListRestaurantEntity)
                .on(qRestaurantEntity.id.eq(qWishListRestaurantEntity.restaurantId))
                .where(qWishListRestaurantEntity.userId.eq(userId)
                        .and(qWishListRestaurantEntity.wishListId.eq(wishListId)))
                .fetch();
    }

    @Transactional
    @Override
    public boolean deleteRestaurantFromWishList(Long userId, Long restaurantId) {
        QWishListRestaurantEntity wishListRestaurant = QWishListRestaurantEntity.wishListRestaurantEntity;

        long deletedCount = jpaQueryFactory.delete(wishListRestaurant)
                .where(wishListRestaurant.userId.eq(userId)
                        .and(wishListRestaurant.restaurantId.eq(restaurantId)))
                .execute();

        return deletedCount > 0;
    }



}
