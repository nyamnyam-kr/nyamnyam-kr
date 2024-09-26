package kr.nyamnyam.model.repository.Custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QWishListEntity;
import kr.nyamnyam.model.entity.WishListEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WishListCustomRepositoryImpl implements WishListCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<WishListEntity> getWishLists(Long userId) {
        QWishListEntity qWishListEntity = QWishListEntity.wishListEntity;

        return jpaQueryFactory.selectFrom(qWishListEntity)
                .distinct()
                .where(qWishListEntity.userId.eq(userId))
                .fetch();
    }


}
