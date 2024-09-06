package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReplyEntity is a Querydsl query type for ReplyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReplyEntity extends EntityPathBase<ReplyEntity> {

    private static final long serialVersionUID = -57250396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReplyEntity replyEntity = new QReplyEntity("replyEntity");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPostEntity post;

    public final QRestaurantEntity restaurant;

    public final QUserEntity user;

    public QReplyEntity(String variable) {
        this(ReplyEntity.class, forVariable(variable), INITS);
    }

    public QReplyEntity(Path<? extends ReplyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReplyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReplyEntity(PathMetadata metadata, PathInits inits) {
        this(ReplyEntity.class, metadata, inits);
    }

    public QReplyEntity(Class<? extends ReplyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPostEntity(forProperty("post")) : null;
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurantEntity(forProperty("restaurant")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

