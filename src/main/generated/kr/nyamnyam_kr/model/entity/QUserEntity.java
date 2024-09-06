package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 1618801271L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> grade = createNumber("grade", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<PostEntity, QPostEntity> postEntityList = this.<PostEntity, QPostEntity>createList("postEntityList", PostEntity.class, QPostEntity.class, PathInits.DIRECT2);

    public final ListPath<ReplyEntity, QReplyEntity> replyEntityList = this.<ReplyEntity, QReplyEntity>createList("replyEntityList", ReplyEntity.class, QReplyEntity.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public final StringPath tel = createString("tel");

    public final StringPath username = createString("username");

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

