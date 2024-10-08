package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.entity.*;
import kr.nyamnyam.model.repository.UpvoteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpvoteRepositoryCustomImpl implements UpvoteRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;
    private UpvoteRepository upvoteRepository;


    @Override
    public UpvoteEntity save(Long postId, String giveId) {
        QPostEntity postEntity = QPostEntity.postEntity;
        QUsersEntity userEntity = QUsersEntity.usersEntity;

        Tuple result = jpaQueryFactory
                .select(postEntity, userEntity)
                .from(postEntity)
                .join(userEntity).on(userEntity.id.eq(postEntity.userId))
                .where(postEntity.id.eq(postId))
                .fetchOne();

        if (result == null) {
            throw new IllegalArgumentException("특정 포스트나 사용자가 없습니다");
        }

        PostEntity post = result.get(postEntity);

        UpvoteEntity newUpvote = new UpvoteEntity();
        newUpvote.setPostId(post.getId());
        newUpvote.setGiveId(giveId);
        newUpvote.setHaveId(post.getUserId());


        return upvoteRepository.save(newUpvote);
    }
}
