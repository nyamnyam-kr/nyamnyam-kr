package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class NoticeModel {
    private Long id;
    private String title;
    private String content;
    private Long hit;
    private Date date;
}
