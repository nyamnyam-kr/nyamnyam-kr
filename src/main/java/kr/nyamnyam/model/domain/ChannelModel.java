package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ChannelModel {

    private String id;

    private String channelId; // 채널 ID
    private String senderId;  // 발신자 ID
    private String content;   // 메시지 내용
    private LocalDateTime timestamp; // 메시지 타임스탬프
}
