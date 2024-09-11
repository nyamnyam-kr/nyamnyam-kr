package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CrawlingInfo {
    private Long id;
    private String title;
    private String name;
    private String address;
    private String postcode;


}
