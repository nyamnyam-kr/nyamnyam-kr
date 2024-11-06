package kr.nyamnyam.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserThumbnailService {
    // 사용자 ID와 이미지를 매개변수로 받아서 썸네일을 업로드하는 메서드
    Mono<String> uploadThumbnail(String userId, List<MultipartFile> images);
}
