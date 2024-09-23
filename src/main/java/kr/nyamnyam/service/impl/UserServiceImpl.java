package kr.nyamnyam.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.nyamnyam.model.domain.UserModel;
import kr.nyamnyam.model.entity.UsersEntity;
import kr.nyamnyam.model.repository.UserRepository;
import kr.nyamnyam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OAuth2Service oAuth2Service; // OAuth2 관련 서비스
   /* private final JwtTokenProvider jwtTokenProvider; // JWT 관련 클래스*/

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<UsersEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UsersEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UsersEntity> findAll() {
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
    public UsersEntity update(UserModel userModel) {
        return userRepository.findById(userModel.getId())
                .map(existingUser -> {
                    return UsersEntity.builder()
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
    public UsersEntity save(UserModel userModel) {
        UsersEntity usersEntity = UsersEntity.builder()
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
        return userRepository.save(usersEntity);
    }

    @Override
    public Optional<UsersEntity> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    // OAuth2 로그인 관련 메서드
    /*@Override
    public String loginWithOAuth2(String code, String receivedState, HttpServletRequest request) {
        // 세션에서 저장된 state 값 가져오기
        String savedState = (String) request.getSession().getAttribute("oauth_state");

        // state 값 검증
        if (!receivedState.equals(savedState)) {
            throw new RuntimeException("Invalid state value, potential CSRF attack");
        }

        try {
            String accessToken = oAuth2Service.getAccessToken(code);
            String userInfo = oAuth2Service.getUserInfo(accessToken);

            // 사용자 정보에서 필요한 정보 추출
            String username = extractUsername(userInfo);

            // 사용자 정보를 이용해 JWT 발급
            String jwtToken = jwtTokenProvider.createToken(username, "ROLE_USER");
            return jwtToken;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to login with OAuth2");
        }
    }*/

   /* private String extractUsername(String userInfo) {
        JSONObject jsonObject = new JSONObject(userInfo);
        return jsonObject.getString("email");
    }

    // OAuth2 인증 시작 메서드
    @Override
    public void startOAuth2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String state = UUID.randomUUID().toString();
        request.getSession().setAttribute("oauth_state", state);

        String authorizationUrl = "https://nid.naver.com/oauth2.0/authorize" +
                "?response_type=code" +
                "&client_id=your_client_id" +
                "&redirect_uri=your_redirect_uri" +
                "&state=" + state;

        response.sendRedirect(authorizationUrl);
    }

    @Override
    public String authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> jwtTokenProvider.createToken(username, user.getRole()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials")); // 예외 처리
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }*/

}

