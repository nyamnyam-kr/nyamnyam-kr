package kr.nyamnyam.model.repository.Custom;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.nyamnyam.model.domain.Chart.ReportCountModel;
import kr.nyamnyam.model.entity.QPostEntity;
import kr.nyamnyam.model.entity.QReportEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ReportCountModel> reportFindAll() {
        QReportEntity report = QReportEntity.reportEntity;
        QPostEntity post = QPostEntity.postEntity;


        List<Tuple> results = jpaQueryFactory
                .select(post.content, report.count(), post.id)
                .from(report)
                .join(post).on(post.id.eq(report.post.id))
                .groupBy(post.id, post.content)
                .fetch();

        return results.stream()
                .map(tuple -> {
                    ReportCountModel reportCountModel = new ReportCountModel();
                    reportCountModel.setContent(tuple.get(post.content));
                    reportCountModel.setCount(tuple.get(report.count()));
                    reportCountModel.setPostId(tuple.get(post.id));
                    return reportCountModel;
                })
                .collect(Collectors.toList());

    }
}
