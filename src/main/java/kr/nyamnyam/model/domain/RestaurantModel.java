package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class RestaurantModel {

    private Long id;
    private String name;
    private String address;
    private String type;
    private Double rate;


}
