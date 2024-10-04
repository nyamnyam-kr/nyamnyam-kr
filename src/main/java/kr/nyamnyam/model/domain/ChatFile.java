package kr.nyamnyam.model.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chatFiles")
public class ChatFile {
    @Id
    private String id; // MongoDB에서 사용되는 ObjectId

    private String chatRoomId;
    private String sender;
    private String fileName;
    private String fileUrl;
    private long size;
    private String contentType; // 파일 형식 (예: image/jpeg)
}
