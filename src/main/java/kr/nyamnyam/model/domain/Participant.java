package kr.nyamnyam.model.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "participants")
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    private String id;
    private String nickname;
    private String chatRoomName;
}