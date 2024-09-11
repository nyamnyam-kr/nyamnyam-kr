package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    private Long postId; // 고유번호 (POST_SN)

    private String langCodeId; // 언어
    private String name; // 상호명
    private String postUrl; // 콘텐츠URL
    private String address; // 주소
    private String phoneNumber; // 전화번호
    private String websiteUrl; // 웹사이트
    private String useTime; // 운영시간
    private String subwayInfo; // 교통정보
    private String representativeMenu; // 대표메뉴
    private String category;
    private String ImageUrl;

}
