package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import kr.nyamnyam_kr.model.domain.RestaurantModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "tel")
    private Long tel;

    @Column(name = "operate_Time")
    private String operateTime;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "modify_date")
    private Date modifyDate;

    @Column(name = "toilet")
    private Long toilet;

    // !!! 은서 : zone이랑 Join 확인 부탁드려요
    @OneToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private ZoneEntity zone;

    @OneToMany(mappedBy = "restaurant")
    private List<PostEntity> postEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<ReplyEntity> replyEntityList;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuEntity> menuEntityList;

    public static RestaurantEntity toRestaurantEntity(RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName(restaurantModel.getName());
        restaurantEntity.setAddress(restaurantModel.getAddress());
        restaurantEntity.setTel(restaurantModel.getTel());
        restaurantEntity.setOperateTime(restaurantModel.getOperateTime());
        restaurantEntity.setToilet(restaurantModel.getToilet());
        return restaurantEntity;
    }

}
