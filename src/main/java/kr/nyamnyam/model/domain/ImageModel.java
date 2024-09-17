package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageModel {
    private Long id;
    private String originalFilename;
    private String storedFileName;
    private String extension;

}
