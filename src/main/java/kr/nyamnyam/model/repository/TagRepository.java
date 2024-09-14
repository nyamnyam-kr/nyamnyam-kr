package kr.nyamnyam.model.repository;

import kr.nyamnyam.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, String> {

    Optional<TagEntity> findByName(String name);
}
