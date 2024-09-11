package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ReplyModel {

    private Long id;
    private String content;

    private Long restaurantId;
    private Long postId;
    private Long userId;
    private String nickname;
}
