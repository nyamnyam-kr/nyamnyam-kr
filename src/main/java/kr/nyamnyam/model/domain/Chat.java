package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chats")
@AllArgsConstructor
@Builder
public class Chat {
    @Id
    private String id;

    private String message; // 채팅 내용
    private LocalDateTime createdAt;
    private String sender;  // 발신자 nickname

    private String chatRoomId;
}
