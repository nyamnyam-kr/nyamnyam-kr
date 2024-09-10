package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "menu")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Long price;

    @Column(name = "menu_name")
    private String menuName;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
