package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageModel {

    private Long id;
    private String content;
    private Long channelId;
    private Long participantId;

}
