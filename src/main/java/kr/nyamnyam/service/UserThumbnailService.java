package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.User;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserThumbnailService {
    Mono<Void> uploadThumbnail(User user, List<MultipartFile> images);
}
