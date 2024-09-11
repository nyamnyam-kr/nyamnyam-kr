package kr.nyamnyam.model.entity;

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



}
