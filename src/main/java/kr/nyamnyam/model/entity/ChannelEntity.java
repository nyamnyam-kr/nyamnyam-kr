package kr.nyamnyam.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "channels")
@AllArgsConstructor
@Builder
public class ChannelEntity {

    @Id
    private String id;
    private String name;
    private List<String> participants; // List of participant IDs

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
