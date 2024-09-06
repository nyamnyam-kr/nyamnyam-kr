package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostEntity is a Querydsl query type for PostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostEntity extends EntityPathBase<PostEntity> {

    private static final long serialVersionUID = 1645800332L;

    public static final QPostEntity postEntity = new QPostEntity("postEntity");

    public final NumberPath<Long> clean = createNumber("clean", Long.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> entryDate = createDateTime("entryDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final NumberPath<Long> service = createNumber("service", Long.class);

    public final NumberPath<Long> taste = createNumber("taste", Long.class);

    public QPostEntity(String variable) {
        super(PostEntity.class, forVariable(variable));
    }

    public QPostEntity(Path<? extends PostEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostEntity(PathMetadata metadata) {
        super(PostEntity.class, metadata);
    }

}

