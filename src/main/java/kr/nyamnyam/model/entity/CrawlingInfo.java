package kr.nyamnyam.model.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "crawlingInfo")
public class CrawlingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String roadAddress;
    private Double rate;




}
