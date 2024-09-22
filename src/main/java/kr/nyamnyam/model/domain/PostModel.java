package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {
    private Long id;
    private String content;
    private Long taste;
    private Long clean;
    private Long service;
    private LocalDateTime entryDate;
    private LocalDateTime modifyDate;

    // 임시로 1값 설정
    private Long userId = 1L;
    private String nickname;
    private Long restaurantId;

    private List<ImageModel> images;

    private double averageRating;
    private List<String> tags;

    public double averageRating() {
        return (this.taste + this.clean + this.service) / 3.0;
    }

//    private Long imgId;   => 없어도 에러 x

}
