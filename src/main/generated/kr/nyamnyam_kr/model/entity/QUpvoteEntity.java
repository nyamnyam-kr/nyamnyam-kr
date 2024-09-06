package kr.nyamnyam_kr.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpvoteEntity is a Querydsl query type for UpvoteEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUpvoteEntity extends EntityPathBase<UpvoteEntity> {

    private static final long serialVersionUID = -658441999L;

    public static final QUpvoteEntity upvoteEntity = new QUpvoteEntity("upvoteEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QUpvoteEntity(String variable) {
        super(UpvoteEntity.class, forVariable(variable));
    }

    public QUpvoteEntity(Path<? extends UpvoteEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpvoteEntity(PathMetadata metadata) {
        super(UpvoteEntity.class, metadata);
    }

}

