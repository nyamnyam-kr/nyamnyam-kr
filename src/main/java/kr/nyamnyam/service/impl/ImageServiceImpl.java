package kr.nyamnyam.service.impl;

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
