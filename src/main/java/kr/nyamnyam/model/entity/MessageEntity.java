package kr.nyamnyam.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
@AllArgsConstructor
@Builder
public class MessageEntity {
    @Id
    private String id;

    private String message; // 채팅 내용
    private LocalDateTime CreatedAt;
    private String sender;  // 발신자 nickname

    private String channelId;   //채팅 방 번호
    private String channelName ; //채팅방 이름

}
