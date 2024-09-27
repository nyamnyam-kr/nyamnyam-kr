package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.domain.NaverMemberInfo;
import kr.nyamnyam.model.domain.User;
import kr.nyamnyam.model.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OAuth2Service {

    private final String clientId = "e2iaB9q3A_kk1k7hX6Qi";
    private final String clientSecret = "Av6eAE_PsV";
    private final String redirectUri = "http://localhost:8080/oauth2/callback";
    private final String tokenUri = "https://nid.naver.com/oauth2.0/token";
    private final String userInfoUri = "https://openapi.naver.com/v1/nid/me";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // 액세스 토큰 받기
    public Mono<String> getAccessToken(String code) {
        String requestBody = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code" +
                "&code=" + code;

        return webClientBuilder.build()
                .post()
                .uri(tokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseAccessToken);
    }

    // 사용자 정보 요청
    public Mono<NaverMemberInfo> getUserInfo(String accessToken) {
        return webClientBuilder.build()
                .get()
                .uri(userInfoUri + "?access_token=" + accessToken)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseUserInfo);
    }

    // 사용자 정보를 User로 변환하고 저장
    public Mono<User> handleNaverLogin(String code) {
        return getAccessToken(code)
                .flatMap(this::getUserInfo)
                .flatMap(naverMemberInfo -> {
                    // 사용자 존재 여부 확인
                    return userRepository.findByUsername(naverMemberInfo.getEmail())
                            .switchIfEmpty(Mono.defer(() -> {
                                // 새 사용자 등록
                                User newUser = User.builder()
                                        .username(naverMemberInfo.getEmail())
                                        .name(naverMemberInfo.getName())
                                        .nickname(naverMemberInfo.getName())
                                        .enabled(true)
                                        .role("ROLE_USER")
                                        .build();
                                return userRepository.save(newUser); // 새 사용자 저장
                            }))
                            .defaultIfEmpty(null); // 기존 사용자 반환
                });
    }

    private String parseAccessToken(String response) {
        // JSON 파싱하여 액세스 토큰 추출
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("access_token");
    }

    private NaverMemberInfo parseUserInfo(String response) {
        // JSON 파싱하여 NaverMemberInfo 생성
        JSONObject jsonObject = new JSONObject(response);
        return new NaverMemberInfo(jsonObject.toMap());
    }
}
