package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ChannelModel {

    private String id; // Channel ID (optional for update, auto-generated for new records)
    private String name; // Channel name
    private List<String> participants; // List of participant IDs
}
