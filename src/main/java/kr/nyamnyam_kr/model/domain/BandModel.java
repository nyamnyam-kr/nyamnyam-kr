package kr.nyamnyam_kr.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class BandModel {

    private Long id;

    private String name;

    private String content;

    private LocalDateTime entryDate;

    private LocalDateTime modifyDate;

    private Long people;

    private Boolean access;

    private String part;

    private String nickname;
}
