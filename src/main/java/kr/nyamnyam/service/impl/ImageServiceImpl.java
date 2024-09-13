package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.ImageRepository;
import kr.nyamnyam.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

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

            repository.save(image);
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
