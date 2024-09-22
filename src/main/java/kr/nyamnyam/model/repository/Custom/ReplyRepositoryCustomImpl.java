package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QReplyEntity;
import kr.nyamnyam.model.entity.QUsersEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findAllByPostWithNickname(Long postId) {
        QReplyEntity replyEntity = QReplyEntity.replyEntity;
        QUsersEntity usersEntity = QUsersEntity.usersEntity;

        return jpaQueryFactory
                .select(replyEntity, usersEntity.nickname)
                .from(replyEntity)
                .join(usersEntity).on(replyEntity.userId.eq(usersEntity.id))
                .where(replyEntity.postId.eq(postId))
                .fetch();
    }
}
