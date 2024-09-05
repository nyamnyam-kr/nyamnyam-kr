package kr.nyamnyam_kr.model.entity;

import jakarta.persistence.*;
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

}
