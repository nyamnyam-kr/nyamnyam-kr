package kr.nyamnyam.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.ImageRepository;
import kr.nyamnyam.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    private final PostServiceImpl postService;
    private final AmazonS3 amazonS3;

    @Value("${naver.storage.bucket}")
    private String bucketName;

    @Override
    public String getFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf("."));
        return System.currentTimeMillis() + ext;
    }

    @Override
    public List<ImageModel> uploadFiles(List<MultipartFile> multipartFiles, String uploadPath, Long postId) {
        List<ImageModel> s3files = new ArrayList<>();

        PostEntity postEntity = postService.findEntityById(postId);
        if (postEntity == null) {
            throw new IllegalArgumentException("Invalid postId: " + postId);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storedFileName = getFileName(originalFilename);
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }
            String uploadURL = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                String keyName = uploadPath + "/" + storedFileName;

                amazonS3.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

                uploadURL = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
            }
            ImageModel imageModel = ImageModel.builder()
                    .originalFilename(originalFilename)
                    .storedFileName(storedFileName)
                    .extension(extension)
                    .uploadPath(uploadPath)
                    .uploadURL(uploadURL)
                    .postId(postEntity.getId())
                    .build();

            s3files.add(imageModel);

            ImageEntity imageEntity = convertToEntity(imageModel);
            imageEntity.setPost(postEntity);
            repository.save(imageEntity);
        }
        return s3files;
    }

    @Override
    public List<ImageEntity> findByPostId(Long postId) {
        return repository.findByPostId(postId);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Boolean insertReceipt(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }


        Path uploadPath = Paths.get("src/main/resources/static/image");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }

        String storedFileName = System.currentTimeMillis() + "." + extension;

        Path filePath = uploadPath.resolve(storedFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        ImageEntity img = ImageEntity.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .extension(extension)
                .build();

        repository.save(img);

        return true;
    }

    @Override
    public List<ImageEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ImageEntity> findById(Long id) {
        return repository.findById(id);
    }

    /*@Override
    public Boolean existsByPostId(Long postId) {
        return repository.existsByPostId(postId);
    }*/

    @Override
    public Boolean deleteById(Long imgId) {
        if(repository.existsById(imgId)) {
            repository.deleteById(imgId);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean save(ImageEntity entity) {
        repository.save(entity);
        return true;
    }

    private ImageModel convertToModel(ImageEntity entity) {
        return ImageModel.builder()
                .originalFilename(entity.getOriginalFileName())
                .storedFileName(entity.getStoredFileName())
                .extension(entity.getExtension())
                .uploadPath(entity.getUploadPath())
                .uploadURL(entity.getUploadURL())
                .build();
    }
    private ImageEntity convertToEntity(ImageModel model) {
        PostEntity postEntity = postService.findEntityById(model.getPostId());

        return ImageEntity.builder()
                .originalFileName(model.getOriginalFilename())
                .storedFileName(model.getStoredFileName())
                .extension(model.getExtension())
                .uploadPath(model.getUploadPath())
                .uploadURL(model.getUploadURL())
                .build();
    }
}
