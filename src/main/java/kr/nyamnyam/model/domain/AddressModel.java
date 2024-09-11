package kr.nyamnyam.model.domain;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AddressModel {

    private String address;
    private double latitude;
    private double longitude;
}

