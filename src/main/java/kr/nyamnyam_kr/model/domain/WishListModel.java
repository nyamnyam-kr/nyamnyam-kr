package kr.nyamnyam_kr.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WishListModel {

    private Long id;
    private String name;
}