package kr.nyamnyam.model.entity;

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

    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
