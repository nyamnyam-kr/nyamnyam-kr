package kr.nyamnyam.model.domain;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReceiptModel {

    private Long id;
    private String price;
    private String name;
    private String menu;


}
