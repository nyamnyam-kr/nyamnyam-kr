package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.model.repository.CrawlingRepository;
import kr.nyamnyam.service.CrawlService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private final CrawlingRepository crawlingRepository;

    @Override
    public void crawlAndSaveInfos() {

        WebDriver webDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        List<CrawlingInfo> crawledList = new ArrayList<>();

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
                CrawlingInfo crawlingInfo = new CrawlingInfo();

                WebElement name2Element = webDriver.findElement(By.cssSelector(".lnJFt"));


                // 상세정보 프레임에서 주소 정보가 들어있는 곳으로 이동
            /*    List<WebElement> addressContents = webDriver.findElements(By.cssSelector(".place_section_content"));
                WebElement homeElement = addressContents.get(i);*/

                // 주소 버튼 요소 찾아 클릭하기
                WebElement addressButton = webDriver.findElement(By.cssSelector("._UCia"));
                addressButton.click();

                // "도로명"과 "우편번호" 정보 들어있는  div로 이동
                WebElement addressDiv = webDriver.findElement(By.className("Y31Sf"));
                List<WebElement> addressInfos = addressDiv.findElements(By.className("nQ7Lh"));

                // span은 주소종류, 안의 text가 필요한 데이터
                for (WebElement addressInfo : addressInfos) {
                    WebElement addressType = addressInfo.findElement(By.tagName("span"));
                    String address = addressInfo.getText().replace(addressType.getText(), "").trim();
                    System.out.println(addressType.getText() + " : " + address);
                    crawlingInfo.setAddress(address);
                }


                String nameText = nameElement.getText();
                String name2Text = name2Element.getText();



                //String addressText = addressElement.getText();
                //String postCodeText = postCodeElement.getText();

                crawlingInfo.setTitle(nameText);
                crawlingInfo.setName(name2Text);



                //crawlingInfo.setAddress(addressText);
                //crawlingInfo.setPostcode(postCodeText);

                crawledList.add(crawlingInfo);

                // 클릭한 요소가 더 이상 유효하지 않을 수 있으므로, 새로 로드된 결과를 가져옴
                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("searchIframe");
                titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            }

            // 크롤링 정보 저장
            crawlingRepository.saveAll(crawledList);
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

    @Override
    public List<CrawlingInfo> getCrawlingInfos() {
        return crawlingRepository.findAll();
    }

    @Override
    public List<CrawlingInfo> getCrawlingInfos(int startRow, int endRow) {
        return crawlingRepository.findCrawlingInRange(startRow, endRow);
    }

    @Override
    public int getTotalCount() {
        return (int) crawlingRepository.count();
    }
}
