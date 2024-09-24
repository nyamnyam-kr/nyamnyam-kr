package kr.nyamnyam.service.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OAuth2Service {

    private final String clientId = "e2iaB9q3A_kk1k7hX6Qi";
    private final String clientSecret = "Av6eAE_PsV";
    private final String redirectUri = "http://localhost:8080/oauth2/callback";
    private final String tokenUri = "https://nid.naver.com/oauth2.0/token";
    private final String userInfoUri = "https://openapi.naver.com/v1/nid/me";

    // 액세스 토큰 받기
    public String getAccessToken(String code) throws IOException {
        String requestBody = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code" +
                "&code=" + code;

        URL url = new URL(tokenUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes());
            os.flush();
        }

        // 응답 처리
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // JSON 파싱 후 액세스 토큰 반환
        return parseAccessToken(response.toString());
    }

    // 사용자 정보 요청
    public String getUserInfo(String accessToken) throws IOException {
        URL url = new URL(userInfoUri + "?access_token=" + accessToken);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken); // 필요한 헤더 추가

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString(); // JSON 형식의 사용자 정보 반환
    }

    private String parseAccessToken(String response) {
        // JSON 파싱하여 액세스 토큰 추출
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("access_token");
    }
}
