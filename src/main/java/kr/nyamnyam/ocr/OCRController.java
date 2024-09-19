package kr.nyamnyam.ocr;

import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.domain.RestaurantModel;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        RestaurantModel res = new RestaurantModel();



//        String address = result.stream()
//                .takeWhile(item -> !item.equals("[대표자]") &&
//                        !(item.equals("소]") &&
//                                result.indexOf(item) + 1 < result.size() &&
//                                result.get(result.indexOf(item) + 1).equals("[대표자]")))
//                .collect(Collectors.joining());

//        int startIndex = IntStream.range(0, result.size())
//                .filter(i -> result.get(i).equals("금액"))
//                .findFirst()
//                .orElse(-1); // "금액"이 없으면 -1 반환
//
//        int endIndex = IntStream.range(0, result.size())
//                .filter(i -> result.get(i).equals("합계"))
//                .findFirst()
//                .orElse(result.size()); // "합계"가 없으면 리스트 크기로 설정
//
//        if (startIndex != -1 && endIndex > startIndex) {
//            List<String> values = result.subList(startIndex + 1, endIndex);
//
//            for (int i = 0; i < values.size(); i++) {
//                switch (i % 4) {
//                    case 0: // menu
//                        String menu = values.get(i);
//                        break;
//                    case 1: // unit
//                        Long unit = Long.parseLong(values.get(i));
//                        break;
//                    case 2: // amount
//                        Long amount = Long.parseLong(values.get(i));
//                        break;
//                    case 3: // price
//                        Long price = Long.parseLong(values.get(i));
//                        break;
//                }
//            }
//        }

        String total = IntStream.range(0, result.size())
                .filter(i -> i < result.size() - 2 &&
                        result.get(i).equals("합계") &&
                        result.get(i + 1).equals("금액"))
                .mapToObj(i -> result.get(i + 2)) // i+2번째 값을 String으로 변환
                .findFirst() // 조건을 만족하는 첫 번째 값을 찾음
                .orElse("");

        System.out.println("name =" + name + "address =" + address + "total =" + total);






        StringBuilder receiptBuilder = new StringBuilder();

        for (String item : result) {
            receiptBuilder.append(item);
        }





        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity insertReceipt(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("OCRController.insertReceipt");
        ImageModel imageModel = imageService.insertReceipt(file);

        String fileName = imageModel.getStoredFileName();
        File file1 = ResourceUtils.getFile("classpath:static/image/" + fileName);

        List<String> result = naverOcrApi.callApi(file1.getPath(), secretKey, "jpg");
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
