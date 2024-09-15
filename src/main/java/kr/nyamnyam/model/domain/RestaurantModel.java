package kr.nyamnyam.model.domain;

import jakarta.persistence.Column;
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
    private String operation;
    private String tel;
    private String menu;
    private String thumbnailImageUrl;
    private String subImageUrl;



}
