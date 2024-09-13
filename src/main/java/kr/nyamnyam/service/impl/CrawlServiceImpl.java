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
            webDriver.get("https://map.naver.com/v5/search/군포 맛집");

            // 결과가 로드될 때까지 대기
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("searchIframe")));
            webDriver.switchTo().defaultContent();
            webDriver.switchTo().frame("searchIframe");

            List<WebElement> titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            for (int i = 0; i < Math.min(titleElements.size(), 3); i++) {
                WebElement titleElement = titleElements.get(i);
                // 검색 결과 클릭
                titleElement.click();
                Thread.sleep(3000);
                titleElements.get(i).click();

                webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

                // 상세보기로 프레임으로 이동
                webDriver.switchTo().defaultContent();
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
                    // 두번재 ._UCia 클릭
                    WebElement operationButton = detailButtons.get(1);
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", operationButton);
                    //operationButton.click();
                    try {
                        WebElement operationDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".A_cdD>.H3ua4")));
                        WebElement operationDay = webDriver.findElement(By.cssSelector(".A_cdD>.i8cJw"));
                        String operationTime = operationDiv.getText().trim();
                        String operationDayText = operationDay.getText().trim();
                        operationTime = operationTime.replace("\n", " ").trim();
                        operationDayText = operationDayText.replace("\n", " ").trim();

                        combinedOperation = operationDayText + " / " + operationTime;
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
                } catch (Exception e) {
                    e.printStackTrace();
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




