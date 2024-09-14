package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.service.CrawlService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

        // 브라우저에 전시되지 않도록
       /* ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        */

        WebDriver webDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        List<RestaurantEntity> crawledList = new ArrayList<>();

        List<String> existingNames = restaurantRepository.findAllNames();

        try {
            webDriver.get("https://map.naver.com/v5/search/서울 맛집");

            // 결과가 로드될 때까지 대기
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("searchIframe")));
            webDriver.switchTo().defaultContent();
            webDriver.switchTo().frame("searchIframe");

            List<WebElement> titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            for (int i = 0; i < Math.min(titleElements.size(), 4); i++) {
                WebElement titleElement = titleElements.get(i);
                // 검색 결과 클릭
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", titleElement);
                wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
                titleElements.get(i).click();

                // 상세보기로 프레임으로 이동
                //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("entryIframe")));
                webDriver.switchTo().defaultContent();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                // (4) 상세정보가 나오는 프레임으로 이동한다.
                webDriver.switchTo().frame("entryIframe");




                WebElement nameElement = webDriver.findElement(By.cssSelector(".GHAhO"));
                WebElement typeElement = webDriver.findElement(By.cssSelector(".lnJFt"));

                // 가게번호
                String telText;
                try {
                    WebElement telInfo = webDriver.findElement(By.cssSelector(".xlx7Q"));
                    telText = telInfo.getText();
                } catch (TimeoutException | NoSuchElementException e) {
                    telText = "번호가 존재하지 않습니다.";
                }

                // 평점
                Double rating;
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".PXMot.LXIwF")));
                    WebElement star = webDriver.findElement(By.cssSelector(".PXMot.LXIwF"));
                    String starText = star.getText().replaceAll("[^0-9.]", "").trim();
                    rating = starText.isEmpty() ? 0.0 : Double.parseDouble(starText);
                } catch (NoSuchElementException | TimeoutException e) {
                    rating = null;
                    System.out.println("평점 정보를 찾을 수 없어 기본값 0.0을 설정합니다.");
                }


                //  (주소, 영업 시간 등의 상세보기) 버튼 요소 찾아 클릭하기
                WebElement addressButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("._UCia")));
                //addressButton = webDriver.findElement(By.cssSelector("._UCia"));
                // 팝업과 같은 장애물이 클릭요소를 가리는 경우가 많아 javaScript 로 강제 클릭
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", addressButton);

                // addressButton.click();

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

                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("entryIframe");

                String combinedOperation = "운영시간 정보가 없습니다";
                List<WebElement> detailButtons = webDriver.findElements(By.cssSelector("._UCia"));

                if (detailButtons.size() >= 2) {
                    WebElement operationButton = detailButtons.get(1);
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", operationButton);

                    try {
                        // 요일별로 다수의 운영시간 정보를 찾아 처리
                        List<WebElement> operationTimes = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".A_cdD>.H3ua4")));
                        List<WebElement> operationDays = webDriver.findElements(By.cssSelector(".A_cdD>.i8cJw"));

                        StringBuilder operationBuilder = new StringBuilder();
                        for (int j = 0; j < operationDays.size(); j++) {
                            String dayText = operationDays.get(j).getText().trim();
                            String timeText = operationTimes.get(j).getText().replace("\n", " ").trim();

                            operationBuilder.append(dayText).append(" / ").append(timeText).append("\n");
                        }

                        // 마지막 줄바꿈 제거
                        if (operationBuilder.length() > 0) {
                            operationBuilder.setLength(operationBuilder.length() - 1);
                        }

                        combinedOperation = operationBuilder.toString();

                    } catch (TimeoutException | NoSuchElementException e) {
                        combinedOperation = "운영시간 정보가 존재하지 않습니다";
                    }
                }


                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("entryIframe");

                // 메뉴 이름과 가격을 동적으로 추출
                StringBuilder combinedMenu = new StringBuilder();
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ipNNM")));
                    List<WebElement> menuItems = webDriver.findElements(By.cssSelector(".ipNNM"));

                    for (int k = 0; k < menuItems.size(); k++) {

                        String menuNameXPath = String.format("//ul/li[%d]/a/div[2]/div[1]/div/span", k + 1);
                        String menuPriceXPath = String.format("//ul/li[%d]/a/div[2]/div[2]/div", k + 1);

                        WebElement menuNameElement = webDriver.findElement(By.xpath(menuNameXPath));
                        WebElement menuPriceElement = webDriver.findElement(By.xpath(menuPriceXPath));

                        String menuName = menuNameElement.getText();
                        String menuPrice = menuPriceElement.getText();

                        combinedMenu.append(menuName).append("-").append(menuPrice).append(",");
                    }

                    // 마지막 ", " 제거
                    if (combinedMenu.length() > 0) {
                        combinedMenu.setLength(combinedMenu.length() - 2);
                    }
                } catch (TimeoutException | NoSuchElementException e) {
                    combinedMenu.append("메뉴정보가 존재하지 않습니다");
                }


                String nameText = nameElement.getText();
                String typeText = typeElement.getText();

                if (!existingNames.contains(nameText)) {
                    RestaurantEntity restaurant = RestaurantEntity.builder()
                            .name(nameText)
                            .type(typeText)
                            .address(address)
                            .rate(rating)
                            .operation(combinedOperation)
                            .tel(telText)
                            .menu(combinedMenu.toString())
                            .build();

                    crawledList.add(restaurant);
                }

                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("searchIframe");
                titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            }

            restaurantRepository.saveAll(crawledList);
            System.out.println("크롤링된 정보: " + crawledList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}




