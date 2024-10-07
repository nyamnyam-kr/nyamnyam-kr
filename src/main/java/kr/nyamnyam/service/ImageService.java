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

    Boolean deleteById(Long id);

    Boolean save(ImageEntity entity);

    String getFileName(String fileName);

    List<ImageModel> uploadFiles(List<MultipartFile> multipartFiles, Long postId);

    ImageModel insertReceipt(MultipartFile file);

    List<Long> findImageIdsByPostId(Long postId);

    List<ImageEntity> findByPostId(Long postId);

    Boolean updateImages(Long postId, List<MultipartFile> multipartFiles);

    List<String> findImagesByRestaurantId(Long restaurantId);
}