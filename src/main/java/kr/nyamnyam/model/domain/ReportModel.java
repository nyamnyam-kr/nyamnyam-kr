package kr.nyamnyam.model.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.nyamnyam.model.entity.ReportEntity;
import kr.nyamnyam.model.entity.ReportReason;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Component
public class ReportModel {

    private Long id;
    private String userId;
    private Long postId;
    private String reason;
    private LocalDateTime entryDate;

}
