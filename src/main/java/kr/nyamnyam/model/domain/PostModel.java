package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

    private List<ImageModel> images;

}
