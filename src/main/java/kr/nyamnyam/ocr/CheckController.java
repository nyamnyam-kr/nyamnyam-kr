package kr.nyamnyam.ocr;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class CheckController {

    private final NaverOcrApi naverOcrApi;

    @Value("${naver.service.secretKey}")
    private String secretKey;

    private static final Logger log = LoggerFactory.getLogger(CheckController.class);

    @GetMapping("/ocr")
    public ResponseEntity ocr() throws IOException {
        String fileName = "test.jpg"; // 파일 이름
        File file = ResourceUtils.getFile("classpath:static/image/" + fileName);

        List<String> result = naverOcrApi.callApi(file.getPath(), secretKey, "jpg");
        if (result != null) {
            for (String s : result) {
                log.info(s);
            }
        } else {
            log.info("No result returned from OCR API.");
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
