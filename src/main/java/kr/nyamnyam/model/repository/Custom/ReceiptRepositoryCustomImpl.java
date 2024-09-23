package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.QReceiptEntity;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TotalModel> totalCountFromName() {
        QReceiptEntity receiptEntity = QReceiptEntity.receiptEntity;

        List<Tuple> results = jpaQueryFactory
                .select(receiptEntity.name, receiptEntity.price.sum())
                .from(receiptEntity)
                .groupBy(receiptEntity.name)
                .orderBy(receiptEntity.price.sum().desc())
                .limit(5)
                .fetch();

        return results.stream()
                .map(tuple -> {
                    TotalModel totalModel = new TotalModel();
                    totalModel.setRestaurantName(tuple.get(receiptEntity.name));
                    totalModel.setTotal(tuple.get(receiptEntity.price.sum()));
                    return totalModel;
                })
                .collect(Collectors.toList());


    }


}
