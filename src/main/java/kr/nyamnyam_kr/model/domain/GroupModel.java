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
public class GroupModel {

    private Long id;
    private String name;
    private String content;
    private Date entryDate;
    private Date modifyDate;
    private Date dDay;
    private Long people;
    private Long userId;
    private Long restaurantId;

    //유저 닉네임
    private String nickname;
    //식당 이름
    private String restaurantName;
}
