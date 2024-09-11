package kr.nyamnyam.service.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UserEntity;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Pagination pagination;

    private final UserRepository userRepository;

    @Override
    public List<UserEntity> crawling(int pageNum, int pageSize) {
        System.out.println("크롤링 확인");

        // WebDriver 설정
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless")); // Headless 모드

        List<UserEntity> allUserEntities = new ArrayList<>();

        try {
            // 접속할 URL (크롤링할 웹 페이지)
            String url = "https://namu.wiki/w/%EB%B0%B0%EC%9A%B0/%ED%95%9C%EA%B5%AD"; // 실제 URL로 변경
            driver.get(url);

            // 데이터 크롤링 로직
            List<WebElement> elements = driver.findElements(By.cssSelector("div.hbU9WWgP a.QfLdZcbX")); // CSS 선택자 사용
            for (WebElement element : elements) {
                // 링크 텍스트를 username으로 사용
                String username = element.getText().trim();

                // href 속성 값을 nickname으로 사용
                String nickname = element.getAttribute("href");

                // " " (빈 문자열 또는 공백만 있는 경우) 를 제외
                if (username.isEmpty() || username.equals(" ")) {
                    continue;
                }

                // UserEntity 객체 생성
                UserEntity userEntity = UserEntity.builder()
                        .username(username)
                        .nickname(nickname)
                        .build();

                allUserEntities.add(userEntity);
            }

        } finally {
            // 브라우저 종료
            driver.quit();
        }

        // Pagination 객체 생성
        pagination = new Pagination(allUserEntities.size(), pageNum);

        // 페이지 범위에 맞게 데이터 자르기
        List<UserEntity> paginatedEntities = allUserEntities.stream()
                .skip(pagination.getStartRow())
                .limit(pageSize)
                .collect(Collectors.toList());

        // 데이터베이스에 저장
        userRepository.saveAll(paginatedEntities);

        return paginatedEntities;
    }


    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity update(UserModel userModel) {
        return userRepository.findById(userModel.getId())
                .map(existingUser -> {
                    return UserEntity.builder()
                            .id(existingUser.getId())
                            .username(Optional.ofNullable(userModel.getUsername()).orElse(existingUser.getUsername()))
                            .password(Optional.ofNullable(userModel.getPassword()).orElse(existingUser.getPassword()))
                            .nickname(Optional.ofNullable(userModel.getNickname()).orElse(existingUser.getNickname()))
                            .name(Optional.ofNullable(userModel.getName()).orElse(existingUser.getName()))
                            .age(Optional.ofNullable(userModel.getAge()).orElse(existingUser.getAge()))
                            .role(Optional.ofNullable(userModel.getRole()).orElse(existingUser.getRole()))
                            .tel(Optional.ofNullable(userModel.getTel()).orElse(existingUser.getTel()))
                            .gender(Optional.ofNullable(userModel.getGender()).orElse(existingUser.getGender()))
                            .enabled(Optional.ofNullable(userModel.getEnabled()).orElse(existingUser.getEnabled()))
                            .build();
                })
                .map(userRepository::save)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserEntity save(UserModel userModel) {
        UserEntity userEntity = UserEntity.builder()
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .nickname(userModel.getNickname())
                .name(userModel.getName())
                .age(userModel.getAge())
                .role(userModel.getRole())
                .tel(userModel.getTel())
                .gender(userModel.getGender())
                .enabled(userModel.getEnabled())
                .build();
        return userRepository.save(userEntity);
    }
}
