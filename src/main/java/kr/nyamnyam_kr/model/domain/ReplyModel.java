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
public class ReplyModel {

    private Long id;
    private String content;

    private Long restaurantId;
    private Long postId;
    private Long userId;
    private String nickname;
}
