package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishListEntity is a Querydsl query type for WishListEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishListEntity extends EntityPathBase<WishListEntity> {

    private static final long serialVersionUID = 2126321809L;

    public static final QWishListEntity wishListEntity = new QWishListEntity("wishListEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<RestaurantEntity, QRestaurantEntity> restaurantEntityList = this.<RestaurantEntity, QRestaurantEntity>createList("restaurantEntityList", RestaurantEntity.class, QRestaurantEntity.class, PathInits.DIRECT2);

    public QWishListEntity(String variable) {
        super(WishListEntity.class, forVariable(variable));
    }

    public QWishListEntity(Path<? extends WishListEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWishListEntity(PathMetadata metadata) {
        super(WishListEntity.class, metadata);
    }

}

