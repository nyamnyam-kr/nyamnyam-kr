package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import kr.nyamnyam_kr.model.domain.RestaurantModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Long tel;

    private String operateTime;

    private Date entryDate;

    private Date modifyDate;


    /*// !!! 은서 : zone이랑 Join 확인 부탁드려요
    @OneToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private ZoneEntity zone;*/

    @OneToMany(mappedBy = "restaurant")
    private List<PostEntity> postEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<ReplyEntity> replyEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuEntity> menuEntityList;

    public static RestaurantEntity toRestaurantEntity (RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setAddress(restaurantModel.getAddress());
        restaurantEntity.setName(restaurantModel.getName());
        restaurantEntity.setTel(restaurantModel.getTel());
        restaurantEntity.setOperateTime(restaurantModel.getOperateTime());
        return restaurantEntity;
    }

}
