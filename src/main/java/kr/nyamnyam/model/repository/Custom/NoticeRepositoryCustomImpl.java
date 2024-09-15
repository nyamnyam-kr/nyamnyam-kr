package kr.nyamnyam.model.repository.Custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import kr.nyamnyam.model.entity.QNoticeEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    public Long updateHits(Long id) {
        QNoticeEntity noticeEntity = QNoticeEntity.noticeEntity;

        long affectedRows = jpaQueryFactory
                .update(noticeEntity)
                .set(noticeEntity.hit, noticeEntity.hit.add(1))
                .where(noticeEntity.id.eq(id))
                .execute();

        return affectedRows;
    }

}

