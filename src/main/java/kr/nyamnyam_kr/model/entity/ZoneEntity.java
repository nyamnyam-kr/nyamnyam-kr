package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="zone")
public class ZoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @OneToOne(mappedBy = "zone")
    private RestaurantEntity restaurant;
}
