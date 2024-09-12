package kr.nyamnyam.model.domain;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StadiumModel {

    private String stadiumId;

    private String stadiumName;

    private String hometeamId;

    private Integer seatCount;

    private String address;

    private String ddd;

    private String tel;
}
