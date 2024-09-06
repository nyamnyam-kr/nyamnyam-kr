package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "wishList")
public class WishListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "wishListEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL )
    private List<RestaurantEntity> restaurantEntityList;
}
