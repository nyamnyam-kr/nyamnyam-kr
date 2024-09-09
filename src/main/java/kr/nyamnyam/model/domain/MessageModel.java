package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageModel {

    private String id;        // 메시지 ID
    private String channelId; // 채널 ID
    private String senderId;  // 발신자 ID
    private String content;   // 메시지 내용
    private LocalDateTime timestamp;

}
