package kr.nyamnyam_kr.model.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestaurantModel {

    private Long id;
    private String name;
    private String address;
    private Long tel;
    private String operateTime;
    private Date entryDate;
    private Date modifyDate;
    private Long toilet;

    //혹시 모른
    private Long zoneId;
}
