package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QReplyEntity;
import kr.nyamnyam.model.entity.QUsersEntity;
import kr.nyamnyam.model.entity.ReplyEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findAllByPostWithNickname(Long postId) {
        QReplyEntity replyEntity = QReplyEntity.replyEntity;
        QUsersEntity usersEntity = QUsersEntity.usersEntity;

        List<Tuple> result = jpaQueryFactory
                .select(replyEntity, usersEntity.nickname)
                .from(replyEntity)
                .leftJoin(usersEntity).on(replyEntity.userId.eq(usersEntity.id))
                .where(replyEntity.postId.eq(postId))
                .fetch();

        String queryString = jpaQueryFactory
                .select(replyEntity, usersEntity.nickname)
                .from(replyEntity)
                .leftJoin(usersEntity).on(replyEntity.userId.eq(usersEntity.id))
                .where(replyEntity.postId.eq(postId))
                .toString();

        result.forEach(tuple -> {
            ReplyEntity reply = tuple.get(replyEntity);
            String userNickname = tuple.get(usersEntity.nickname);
        });
        return result;
    }

    @Override
    public Tuple findByIdWithNickname(Long replyId){
        QReplyEntity replyEntity = QReplyEntity.replyEntity;
        QUsersEntity usersEntity = QUsersEntity.usersEntity;

        return jpaQueryFactory
                .select(replyEntity, usersEntity.nickname)
                .from(replyEntity)
                .leftJoin(usersEntity).on(replyEntity.userId.eq(usersEntity.id))
                .where(replyEntity.id.eq(replyId))
                .fetchOne();
    }
}
