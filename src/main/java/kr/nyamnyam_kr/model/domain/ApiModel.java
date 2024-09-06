package kr.nyamnyam_kr.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApiModel {

    private String key; // 인증키

    private String type; // 요청 파일 타입

    private String service; // 서비스명

    private Integer startIndex; // 요청 시작 위치

    private Integer endIndex; // 요청 종류 위치

}
