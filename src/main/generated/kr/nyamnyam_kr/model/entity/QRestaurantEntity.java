package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurantEntity is a Querydsl query type for RestaurantEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantEntity extends EntityPathBase<RestaurantEntity> {

    private static final long serialVersionUID = 1512825257L;

    public static final QRestaurantEntity restaurantEntity = new QRestaurantEntity("restaurantEntity");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> entryDate = createDateTime("entryDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<MenuEntity, QMenuEntity> menuEntityList = this.<MenuEntity, QMenuEntity>createList("menuEntityList", MenuEntity.class, QMenuEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final StringPath name = createString("name");

    public final StringPath operateTime = createString("operateTime");

    public final ListPath<PostEntity, QPostEntity> postEntityList = this.<PostEntity, QPostEntity>createList("postEntityList", PostEntity.class, QPostEntity.class, PathInits.DIRECT2);

    public final ListPath<ReplyEntity, QReplyEntity> replyEntityList = this.<ReplyEntity, QReplyEntity>createList("replyEntityList", ReplyEntity.class, QReplyEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> tel = createNumber("tel", Long.class);

    public final NumberPath<Long> toilet = createNumber("toilet", Long.class);

    public QRestaurantEntity(String variable) {
        super(RestaurantEntity.class, forVariable(variable));
    }

    public QRestaurantEntity(Path<? extends RestaurantEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantEntity(PathMetadata metadata) {
        super(RestaurantEntity.class, metadata);
    }

}

