/*
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

        if (postUrl == null || postUrl.trim().isEmpty()) {
            return "https://example.com/path/to/default-image.jpg";
        }

        try {
            Document doc = Jsoup.connect(postUrl).get();

            // 웹 페이지에서 이미지 요소를 찾습니다
            Elements imgElements = doc.select("img");


            for (Element img : imgElements) {
                String imgUrl = img.absUrl("src");
                if (imgUrl.contains("/comm/getImage?srvcId=MEDIA&parentSn=")) {
                    return imgUrl;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "https://example.com/path/to/default-image.jpg";
    }
}
*/
