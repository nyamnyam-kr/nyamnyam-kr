package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.QReplyEntity;
import kr.nyamnyam.model.entity.QUsersEntity;
import kr.nyamnyam.model.entity.ReplyEntity;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
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

        System.out.println("Generated SQL: " + queryString);

        System.out.println("댓글 Custom size : " + result.size());
        result.forEach(tuple -> {
            ReplyEntity reply = tuple.get(replyEntity);
            String userNickname = tuple.get(usersEntity.nickname);
            System.out.println("ReplyEntity : " + reply.getId() + ", nickname: " + userNickname);
        });
        return result;
    }
}
