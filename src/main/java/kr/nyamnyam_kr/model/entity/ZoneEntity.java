package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name="zone")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ZoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToOne(mappedBy = "zone")
    private RestaurantEntity restaurant;
}
