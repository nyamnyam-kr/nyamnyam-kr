package kr.nyamnyam.model.domain.Chart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCountModel {
    private Long postId;
    private String content;
    private Long count;

}
