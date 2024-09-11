package kr.nyamnyam.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class AddressEntity {
    @Id
    private String address;
    private double latitude;
    private double longtitde;

}
