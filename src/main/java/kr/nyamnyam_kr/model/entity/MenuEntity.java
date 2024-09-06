package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "menu")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private String name;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurantEntity;

}
