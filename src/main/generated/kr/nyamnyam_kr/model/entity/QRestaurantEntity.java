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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurantEntity restaurantEntity = new QRestaurantEntity("restaurantEntity");

    public final StringPath address = createString("address");

    public final QCategoryEntity categoryEntity;

    public final StringPath langCodeId = createString("langCodeId");

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final StringPath postUrl = createString("postUrl");

    public final StringPath representativeMenu = createString("representativeMenu");

    public final StringPath subwayInfo = createString("subwayInfo");

    public final StringPath useTime = createString("useTime");

    public final StringPath websiteUrl = createString("websiteUrl");

    public final QWishListEntity wishListEntity;

    public QRestaurantEntity(String variable) {
        this(RestaurantEntity.class, forVariable(variable), INITS);
    }

    public QRestaurantEntity(Path<? extends RestaurantEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurantEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurantEntity(PathMetadata metadata, PathInits inits) {
        this(RestaurantEntity.class, metadata, inits);
    }

    public QRestaurantEntity(Class<? extends RestaurantEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryEntity = inits.isInitialized("categoryEntity") ? new QCategoryEntity(forProperty("categoryEntity"), inits.get("categoryEntity")) : null;
        this.wishListEntity = inits.isInitialized("wishListEntity") ? new QWishListEntity(forProperty("wishListEntity")) : null;
    }

}

