package kr.nyamnyam.model.domain;

import kr.nyamnyam.model.entity.TagCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagModel {
    private String name;
    private TagCategory tagCategory;
}
