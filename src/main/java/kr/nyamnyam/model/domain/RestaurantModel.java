package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class RestaurantModel {

    private Long id;
    private String name;
    private String type;
    private String roadAddress;

}
