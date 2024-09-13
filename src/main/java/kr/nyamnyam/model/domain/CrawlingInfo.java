package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CrawlingInfo {
    private Long id;
    private String name;
    private String type;
    private String roadAddress;




}
