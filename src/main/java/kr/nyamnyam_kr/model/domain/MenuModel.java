package kr.nyamnyam_kr.model.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuModel {

    private Long id;
    private Long price;
    private String menuName;

    private Long restaurantId;
}
