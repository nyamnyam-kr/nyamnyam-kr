package kr.nyamnyam.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 음식점 이름 (POST_SJ)
    private String address; // 주소 (ADDRESS)
    private String newAddress; // 새 주소 (NEW_ADDRESS)
    private String phoneNumber; // 전화번호 (CMMN_TELNO)
    private String subwayInfo; // 지하철 정보 (SUBWAY_INFO)
    private String menu; // 대표 메뉴 (FD_REPRSNT_MENU)

}
