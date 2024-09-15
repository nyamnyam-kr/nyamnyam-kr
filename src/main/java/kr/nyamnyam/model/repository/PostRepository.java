package kr.nyamnyam.model.repository;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.Custom.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<PostEntity, Long>, PostRepositoryCustom {

}

