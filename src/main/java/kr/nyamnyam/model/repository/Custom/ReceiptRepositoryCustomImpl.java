package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.QReceiptEntity;
import kr.nyamnyam.model.entity.QRestaurantEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.sum;


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


    // 가게 매출
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

    @Override
    public List<CostModel> costList(Long userId) {
        QReceiptEntity receiptEntity = QReceiptEntity.receiptEntity;

        List<Tuple> results = jpaQueryFactory
                .select(
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", receiptEntity.date, "%Y-%m").as("formatted_month"),
                        sum(receiptEntity.price).as("total_price_sum")
                )
                .from(receiptEntity)
                .where(receiptEntity.userId.eq(userId))
                .groupBy(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", receiptEntity.date, "%Y-%m")) // 그룹화할 때 포맷된 날짜 사용
                .fetch();

        return results.stream()
                .map(tuple -> {
                    CostModel costModel = new CostModel();
                    costModel.setDate(tuple.get(0, String.class)); // 포맷된 월
                    costModel.setPrice(tuple.get(1, Long.class)); // 총 가격 합계
                    return costModel;
                })
                .collect(Collectors.toList());

    }


}
