package kr.nyamnyam_kr.model.repository;

import kr.nyamnyam_kr.model.domain.UserModel;
import kr.nyamnyam_kr.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :#{#userModel.username} AND u.password = :#{#userModel.password}")
    Map<String, Object> login(@Param("userModel") UserModel userModel);

    UserModel findByUsernameAndPassword(String username, String password);
}
