package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.CrawlService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {

    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;


    @Override
    public void crawlAndSaveInfos() {


        WebDriver webDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        List<RestaurantEntity> crawledList = new ArrayList<>();

        try {
            webDriver.get("https://map.naver.com/v5/search/서울 맛집");

            // 대기하여 검색 결과가 로드될 때까지 기다립니다
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("searchIframe")));
            webDriver.switchTo().defaultContent();
            webDriver.switchTo().frame("searchIframe");

            // 검색 결과의 타이틀을 가져와서 클릭
            List<WebElement> titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            for (int i = 0; i < Math.min(titleElements.size(), 2); i++) {
                WebElement titleElement = titleElements.get(i);

                // 검색 결과 클릭
                titleElement.click();
                Thread.sleep(3000); // 페이지가 로드되기를 기다리기 위해 잠시 대기
                titleElements.get(i).click();

                webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

                // 정보가 포함된 iframe으로 전환
                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("entryIframe");


                // 이름을 가져오기
                WebElement nameElement = webDriver.findElement(By.cssSelector(".GHAhO"));

                // CrawlingInfo 객체에 저장
                RestaurantEntity restaurant = new RestaurantEntity();

                // 음식 종류
                WebElement typeElement = webDriver.findElement(By.cssSelector(".lnJFt"));

                // 별점
                Double rating;
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".PXMot.LXIwF")));
                    WebElement star = webDriver.findElement(By.cssSelector(".PXMot.LXIwF"));

                    String starText = star.getText().replaceAll("[^0-9.]", "").trim();
                    rating = starText.isEmpty() ? 0.0 : Double.parseDouble(starText);
                } catch (NoSuchElementException e) {
                    rating = 0.0;
                }



                // (주소, 영업 시간 등의 상세보기) 버튼 요소 찾아 클릭하기
                WebElement addressButton = webDriver.findElement(By.cssSelector("._UCia"));
                addressButton.click();


                // "도로명"과 "우편번호" 정보 들어있는  div로 이동
                WebElement addressDiv = webDriver.findElement(By.className("Y31Sf"));
                List<WebElement> addressInfos = addressDiv.findElements(By.className("nQ7Lh"));

                String address = "";

                for (WebElement addressInfo : addressInfos) {
                    WebElement addressType = addressInfo.findElement(By.tagName("span"));

                    String addressDetail = addressInfo.getText().replace(addressType.getText(), "").trim();

                    if (addressType.getText().equals("도로명")) {
                        address = addressDetail.replace("복사", "").trim();
                    }
                }



                List<WebElement> detailButtons = webDriver.findElements(By.cssSelector("._UCia"));
                if (detailButtons.size() >= 2) {
                    // 두 번째 버튼 클릭
                    WebElement operationButton = detailButtons.get(1);
                    operationButton.click();

                    try {
                        WebElement operationDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".A_cdD>.H3ua4")));
                        WebElement operationDay = webDriver.findElement(By.cssSelector(".A_cdD>.i8cJw")); // cssSelector에서 .을 빼야 클래스 선택자

                        // 운영시간과 운영요일 텍스트 추출
                        String operationTime = operationDiv.getText().trim();
                        String operationDayText = operationDay.getText().trim();
                        operationTime = operationTime.replace("\n", " ").trim();
                        operationDayText = operationDayText.replace("\n", " ").trim();

                        // 운영시간과 운영요일을 합쳐서 저장
                        String combinedOperation =  operationDayText + " / " + operationTime;

                        restaurant.setOperation(combinedOperation);

                    } catch (TimeoutException e) {
                        System.out.println("운영시간 정보를 찾을 수 없습니다: " + e.getMessage());
                    } catch (NoSuchElementException e) {
                        restaurant.setOperation("운영시간 정보가 없습니다");
                    }
                } else {
                    System.out.println("운영시간 정보를 클릭할 버튼을 찾을 수 없습니다.");
                }

                String nameText = nameElement.getText();
                String typeText = typeElement.getText();


                restaurant.setName(nameText);
                restaurant.setType(typeText);
                restaurant.setAddress(address);
                restaurant.setRate(rating);
                //restaurant.setOperation(operationText);


                crawledList.add(restaurant);

                // 클릭한 요소가 더 이상 유효하지 않을 수 있으므로, 새로 로드된 결과를 가져옴
                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("searchIframe");
                titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            }

            RestaurantEntity restaurant = new RestaurantEntity();
            // 크롤링 정보 저장
            //if (restaurantService.existsByNameAndAddress(restaurant.getName(), restaurant.getAddress())){
            restaurantRepository.saveAll(crawledList);
            //}

            System.out.println(crawledList);
            System.out.println("메서드 종료");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }


}



