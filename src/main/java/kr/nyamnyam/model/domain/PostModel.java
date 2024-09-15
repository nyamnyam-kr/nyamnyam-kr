package kr.nyamnyam.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    private LocalDateTime entryDate;
    private LocalDateTime modifyDate;

    // 추가 예정 private Long restId;


    private List<ImageModel> images;

    private double averageRating;
    private List<String> tags;

    public double averageRating() {
        return (this.taste + this.clean + this.service) / 3.0;
    }

//    private Long imgId;   => 없어도 에러 x

}
