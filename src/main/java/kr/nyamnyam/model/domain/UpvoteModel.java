package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UpvoteModel {
    private Long id;
    private Long giveId;
    private Long haveId;
    private Long postId;
}
