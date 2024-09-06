package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QZoneEntity is a Querydsl query type for ZoneEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QZoneEntity extends EntityPathBase<ZoneEntity> {

    private static final long serialVersionUID = -871105128L;

    public static final QZoneEntity zoneEntity = new QZoneEntity("zoneEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QZoneEntity(String variable) {
        super(ZoneEntity.class, forVariable(variable));
    }

    public QZoneEntity(Path<? extends ZoneEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QZoneEntity(PathMetadata metadata) {
        super(ZoneEntity.class, metadata);
    }

}

