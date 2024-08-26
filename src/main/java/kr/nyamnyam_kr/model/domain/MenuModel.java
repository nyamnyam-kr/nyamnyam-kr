package kr.nyamnyam_kr.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MenuModel {

    private Long id;
    private Long price;
    private String menuName;

    private Long restaurantId;
}
