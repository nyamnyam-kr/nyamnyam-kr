package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ZoneModel {

    private Long id;
    private String name;
}
