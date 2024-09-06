package kr.nyamnyam.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
@AllArgsConstructor
@Builder
public class MessageEntity {
    @Id
    private String id;

    private String channelId; // 채널 ID
    private String senderId;  // 발신자 ID
    private String content;   // 메시지 내용
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now(); // 자동으로 현재 시간 설정
}
