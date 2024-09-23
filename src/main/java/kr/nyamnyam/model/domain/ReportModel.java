package kr.nyamnyam.model.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.nyamnyam.model.entity.ReportReason;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReportModel {

    private Long id;
    private Long userId;
    private Long postId;
    private String reason;

}
