package kr.nyamnyam.model.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class ReportModel {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Date entryDate;
    private String state;

}
