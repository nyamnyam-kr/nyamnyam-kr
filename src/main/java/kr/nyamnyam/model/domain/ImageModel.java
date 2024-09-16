package kr.nyamnyam.model.domain;

import kr.nyamnyam.model.entity.ImageEntity;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class ImageModel {
    private Long id;
    private String originalFilename;
    private String storedFileName;
    private String extension;



}

