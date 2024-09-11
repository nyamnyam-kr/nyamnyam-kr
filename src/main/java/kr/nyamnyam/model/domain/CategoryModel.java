package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CategoryModel {

    private Long id;
    private String name;
}
