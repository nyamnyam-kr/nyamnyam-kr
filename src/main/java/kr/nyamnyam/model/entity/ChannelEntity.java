package kr.nyamnyam.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "channels")
@AllArgsConstructor
@Builder
public class ChannelEntity {

    @Id
    private String id;
    private String name;

    @DBRef
    private List<ParticipantEntity> participants; //참가자 리스트

    @DBRef
    private List<MessageEntity> messages;

}
