package kr.nyamnyam.model.domain.Chart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckModel {
    String name;
    String menu;
    String address;
    String tel;
    Long price;
    Long restaurantId;
    Long check;

}
