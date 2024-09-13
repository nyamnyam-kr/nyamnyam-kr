package kr.nyamnyam.service.impl;

import kr.nyamnyam.service.RestImgService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RestImgServiceImpl implements RestImgService {

    @Override
    public String extractImageUrl(String postUrl) {
        // URL이 비어있거나 null인 경우 처리
        if (postUrl == null || postUrl.trim().isEmpty()) {
            return "https://example.com/path/to/default-image.jpg"; // 기본 이미지 URL
        }

        try {
            // 웹 페이지를 가져옵니다
            Document doc = Jsoup.connect(postUrl).get();

            // 웹 페이지에서 이미지 요소를 찾습니다
            Elements imgElements = doc.select("img");

            // 특정 도메인 또는 경로를 필터링할 수 있습니다. 필요에 따라 조정하십시오.
            for (Element img : imgElements) {
                String imgUrl = img.absUrl("src");
                if (imgUrl.contains("korean.visitseoul.net/restaurants")) {
                    return imgUrl;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "https://example.com/path/to/default-image.jpg"; // 기본 이미지 URL
    }
}
