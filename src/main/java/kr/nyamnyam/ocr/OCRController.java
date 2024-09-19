package kr.nyamnyam.ocr;

import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
@CrossOrigin(origins = "http://localhost:3000")
public class OCRController {

    private final NaverOcrApi naverOcrApi;
    private final ImageService imageService;


    @Value("${naver.service.secretKey}")
    private String secretKey;

    private static final Logger log = LoggerFactory.getLogger(OCRController.class);

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

    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertReceipt(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("OCRController.insertReceipt");


        return ResponseEntity.ok(imageService.insertReceipt(file));

    }


}
