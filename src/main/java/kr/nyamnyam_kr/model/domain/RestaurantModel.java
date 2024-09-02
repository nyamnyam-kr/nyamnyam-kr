package kr.nyamnyam_kr.model.domain;

import kr.nyamnyam_kr.model.entity.RestaurantEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class RestaurantModel {

    private Long id;
    private String name;
    private String address;
    private Long tel;
    private String operateTime;
    private Date entryDate;
    private Date modifyDate;
    private Long toilet;

    //혹시 모른
    private Long zoneId;


    public static RestaurantModel toRestaurantModel(RestaurantEntity restaurantEntity){
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurantEntity.getId());
        restaurantModel.setName(restaurantEntity.getName());
        restaurantModel.setAddress(restaurantEntity.getAddress());
        restaurantModel.setTel(restaurantEntity.getTel());
        restaurantModel.setOperateTime(restaurantEntity.getOperateTime());
        restaurantModel.setEntryDate(restaurantEntity.getEntryDate());

        return restaurantModel;
    }


}
