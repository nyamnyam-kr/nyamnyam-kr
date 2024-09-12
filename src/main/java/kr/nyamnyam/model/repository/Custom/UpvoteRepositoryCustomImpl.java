package kr.nyamnyam.model.repository.Custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UpvoteRepositoryCustomImpl implements UpvoteRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;


}
