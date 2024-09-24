package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.ImageEntity;
import kr.nyamnyam.model.repository.Custom.ImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long>, ImageRepositoryCustom {

    List<ImageEntity> findByPostId(Long postId);

    Boolean existsByPostId(Long postId);

    Boolean deleteByPostId(Long postId);
}
