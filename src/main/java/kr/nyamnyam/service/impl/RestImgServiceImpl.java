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

    //jsoup이라는 라이브러리를 사용해 웹 크롤링으로 이미지 추출
    public String extractImageUrl(String postUrl) {
        try {

            Document doc = Jsoup.connect(postUrl).get();

            Elements metaTags = doc.getElementsByTag("meta");
            for (Element metaTag : metaTags) {
                if ("og:image".equals(metaTag.attr("property"))) {
                    return metaTag.attr("content");
                }
            }

            // 기본 이미지 URL 반환
            return "https://example.com/default-image.jpg";
        } catch (IOException e) {
            e.printStackTrace();
            return "https://example.com/default-image.jpg";
        }
    }
}
