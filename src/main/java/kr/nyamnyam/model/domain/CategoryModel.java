package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class CategoryModel {
    private String name;
    private String category;

}
