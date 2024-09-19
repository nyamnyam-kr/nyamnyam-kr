package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReceiptModel {

    private Long id;
    private String receipt;
}
