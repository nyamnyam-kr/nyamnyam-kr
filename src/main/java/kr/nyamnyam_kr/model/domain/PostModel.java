package kr.nyamnyam_kr.model.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import kr.nyamnyam_kr.model.entity.TagEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
public class PostModel {

    private Long id;
    private String content;
    private Long taste;
    private Long clean;
    private Long service;
    private Date entryDate;
    private Date modifyDate;

}
