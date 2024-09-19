package kr.nyamnyam.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.ImageRepository;
import kr.nyamnyam.service.ImageService;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Long;
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
    private final AmazonS3Client amazonS3Client;

    @Value("${naver.storage.bucket}")
    private String bucketName;

    // 클라우드 스토리지 연결 시 삭제 예정 : + saveImages
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String getFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf("."));
        return System.currentTimeMillis() + ext;
    }
    /*@Override
    public List<ImageModel> uploadFilesSample(List<MultipartFile> multipartFiles) {
        return uploadFiles(multipartFiles, "sample-folder");
    }*/
    @Override
    public List<ImageModel> uploadFiles(List<MultipartFile> multipartFiles, String uploadPath, Long postId){
        List<ImageModel> s3files = new ArrayList<>();

        PostEntity postEntity = postService.findEntityById(postId);
        if(postEntity == null){
            throw new IllegalArgumentException("Invalid postId: " + postId);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storedFileName = getFileName(originalFilename);
            String uploadURL = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try(InputStream inputStream = multipartFile.getInputStream()){
                String keyName = uploadPath + "/" + storedFileName;

                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

                uploadURL = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
            } catch (IOException e) {
                e.printStackTrace();
            }
            s3files.add(
                    ImageModel.builder()
                            .originalFilename(originalFilename)
                            .storedFileName(storedFileName)
                            .uploadPath(uploadPath)
                            .uploadURL(uploadURL)
                            .postId(postEntity.getId())
                            .build()
            );
        }
        return s3files;
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
    public Boolean saveImages(List<MultipartFile> files, PostEntity entity) {
        for (MultipartFile file : files) {
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String storedFilename = System.currentTimeMillis() + extension;

                File destFile = new File(uploadDir + "/" + storedFilename);
                file.transferTo(destFile);

                ImageEntity image = ImageEntity.builder()
                        .originalFileName(originalFilename)
                        .storedFileName(storedFilename)
                        .extension(extension)
                        .post(entity)
                        .build();

                repository.save(image);
                System.out.println("saveImage(ID): " + image.getId());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
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

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean save(ImageEntity entity) {
        repository.save(entity);
        return true;
    }
}
