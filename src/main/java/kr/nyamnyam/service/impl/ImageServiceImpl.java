package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.ImageRepository;
import kr.nyamnyam.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public ImageModel insertReceipt(MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }


        Path uploadPath = Paths.get("src/main/resources/static/image");
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageEntity img = ImageEntity.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .extension(extension)
                .build();

        ImageEntity save = repository.save(img);

        ImageModel imageModel = ImageModel.builder()
                .originalFilename(save.getOriginalFileName())
                .storedFileName(save.getStoredFileName())
                .extension(save.getExtension())
                .build();

        return imageModel;
    }

    @Override
    public Boolean saveImages(List<MultipartFile> files, PostEntity entity) {
        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String storedFilename = UUID.randomUUID().toString() + extension;

                File destFile = new File(uploadDir +"/" + storedFilename);
                file.transferTo(destFile);

                ImageEntity image = ImageEntity.builder()
                        .originalFileName(originalFilename)
                        .storedFileName(storedFilename)
                        .extension(extension)
                        .post(entity)
                        .build();

                repository.save(image);
                System.out.println("Image ID after saving: " + image.getId());
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
    public Optional<ImageEntity> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(UUID id) {
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
