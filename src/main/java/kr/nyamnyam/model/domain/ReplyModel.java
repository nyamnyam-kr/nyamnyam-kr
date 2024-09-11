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
    private Long upvoteId;
    private Long userId;
    private Long postId;
}

