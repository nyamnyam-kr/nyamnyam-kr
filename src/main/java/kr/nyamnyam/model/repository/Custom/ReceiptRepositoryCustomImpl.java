package kr.nyamnyam.model.repository.Custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QReceiptEntity;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReceiptRepositoryCustomImpl implements ReceiptRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public Long findRestaurantId(String name) {
        QReceiptEntity receiptEntity = QReceiptEntity.receiptEntity;
        QRestaurantEntity restaurantEntity = QRestaurantEntity.restaurantEntity;

        return jpaQueryFactory.select(restaurantEntity.id)
                .from(receiptEntity)
                .join(restaurantEntity).on(restaurantEntity.name.eq(name))
                .where(receiptEntity.id.eq(receiptEntity.id))
                .fetchOne();
    }


}
