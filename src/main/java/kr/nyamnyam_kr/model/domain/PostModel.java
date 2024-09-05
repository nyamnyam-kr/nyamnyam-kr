package kr.nyamnyam_kr.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class PostModel {

    private Long id;
    private String content;
    private float rating;   // 친절도,맛,청결
    private Date entryDate;
    private Date modifyDate;

    private Long userId;
    private Long restaurantId;
}
