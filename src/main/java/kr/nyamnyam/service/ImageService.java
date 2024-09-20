package kr.nyamnyam.service;

import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    List<ImageEntity> findAll();

    Optional<ImageEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Boolean save(ImageEntity entity);

    // Boolean saveImages(List<MultipartFile> files, PostEntity post);

    String getFileName(String fileName);

    //List<ImageModel> uploadFilesSample(List<MultipartFile> multipartFiles);

    List<ImageModel> uploadFiles(List<MultipartFile> multipartFiles, String uploadPath, Long postId);

    Boolean insertReceipt(MultipartFile file) throws IOException;

    List<ImageEntity> findByPostId(Long postId);

    //Boolean existsByPostId(Long postId);

    //Boolean deleteByPostId(Long postId);
}
