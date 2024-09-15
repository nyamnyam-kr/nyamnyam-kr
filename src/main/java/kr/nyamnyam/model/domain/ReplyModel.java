package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
public class ReplyModel {
    private Long id;
    private String content;

    private LocalDateTime entryDate;
    private LocalDateTime modifyDate;

    private Long postId;

    private Long userId;


    // 릴리즈 0.01에서 넘어온 부분
//    private Long upvoteId;
//    private Long userId;
//    private Long taste;
//    private Long clean;
//    private Long service;
}

