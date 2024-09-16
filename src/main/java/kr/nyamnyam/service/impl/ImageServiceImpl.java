package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.ImageRepository;
import kr.nyamnyam.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    @Override
    public Boolean insertRecipe(MultipartFile file) throws IOException {
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
            String originalFilename = file.getOriginalFilename();
            String storedFilename = UUID.randomUUID().toString();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            ImageEntity image = ImageEntity.builder()
                    .originalFileName(originalFilename)
                    .storedFileName(storedFilename)
                    .extension(extension)
                    .post(entity)
                    .build();

            // 이미지 엔티티를 저장
            repository.save(image);
        }
        return true; // 성공 여부 반환
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
