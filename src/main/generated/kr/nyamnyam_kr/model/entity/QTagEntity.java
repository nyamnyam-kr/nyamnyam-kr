package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagEntity is a Querydsl query type for TagEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagEntity extends EntityPathBase<TagEntity> {

    private static final long serialVersionUID = -999622636L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTagEntity tagEntity = new QTagEntity("tagEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QPostEntity postEntity;

    public QTagEntity(String variable) {
        this(TagEntity.class, forVariable(variable), INITS);
    }

    public QTagEntity(Path<? extends TagEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTagEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTagEntity(PathMetadata metadata, PathInits inits) {
        this(TagEntity.class, metadata, inits);
    }

    public QTagEntity(Class<? extends TagEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new QPostEntity(forProperty("postEntity")) : null;
    }

}

