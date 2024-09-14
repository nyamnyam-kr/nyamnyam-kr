package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class ReplyModel {
    private Long id;
    private String content;

    private Date entryDate;
    private Date modifyDate;

    private Long postId;


    // 릴리즈 0.01에서 넘어온 부분
//    private Long upvoteId;
//    private Long userId;
//    private Long taste;
//    private Long clean;
//    private Long service;
}

