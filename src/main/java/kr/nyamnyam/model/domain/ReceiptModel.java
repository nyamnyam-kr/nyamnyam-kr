package kr.nyamnyam.model.domain;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class ReceiptModel {

    private Long id;
    private Long price;
    private String name;
    private String menu;
    private Long userId;
    private String date;
    private LocalDateTime entryDate;


}
