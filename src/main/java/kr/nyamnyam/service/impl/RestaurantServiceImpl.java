package kr.nyamnyam.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kr.nyamnyam.model.domain.RestaurantModel;
import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.model.repository.RestaurantRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantEntity save(RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = RestaurantEntity.toRestaurantEntity(restaurantModel);
        return restaurantRepository.save(restaurantEntity);
    }

    @Override
    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public RestaurantEntity findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id " + id));
    }


    @Override
    public Boolean deleteById(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void deleteAll(List<Long> restaurantsIds) {
        restaurantRepository.deleteAllByIds(restaurantsIds);
    }


    @Override
    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    @Override
    public Long count() {
        return restaurantRepository.count();
    }

    @Override
    public List<RestaurantEntity> findAllPage(int page, int pageSize) {
        List<RestaurantEntity> list = findAll();
        Long totalCount = count();

        Pagination pagination = new Pagination(page, totalCount.intValue(), pageSize);
        int startRow = pagination.getStartRow();
        int endRow = pagination.getEndRow();
        List <RestaurantEntity> restaurantEntities = new ArrayList<>();

        for (int i = startRow; i <= endRow && i < list.size(); i++){
            // i가 endRow와 같을 때 반드시 list.size보다는 작아야 한다.
            restaurantEntities.add(list.get(i));
        }
        return restaurantEntities;
    }

    @Override
    public List<RestaurantEntity> findAllUserName(String username) {
       return restaurantRepository.findByName(username);
    }


    @Override
    public void getNew() {
        final String url = "https://m.tripinfo.co.kr/trip_list.html?mode=food_rank&addr=seoul";

        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
            Elements eles = doc.select("div.title.b");

            // 크롤링한 데이터 리스트 생성
            List<RestaurantEntity> restaurantEntities = new ArrayList<>();

            for (Element element : eles) {
                String name = element.text().trim();

                // 새로운 RestaurantEntity 생성 및 설정
                RestaurantEntity restaurant = new RestaurantEntity();
                restaurant.setName(name);





                // 리스트에 추가
                restaurantEntities.add(restaurant);
            }

            // 데이터베이스에 저장
            restaurantRepository.saveAll(restaurantEntities);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void crawlingBot() {

        String baseUrl = "https://m.tripinfo.co.kr/trip_list.html?mode=food_rank&addr=seoul";
        WebDriver driver = new ChromeDriver();
        driver.get(baseUrl);

        // CSS 선택자를 사용하여 클래스 이름으로 요소 찾기
        List<WebElement> nameList = driver.findElements(By.cssSelector("div.title.b"));
        List<WebElement> addressList = driver.findElements(By.cssSelector("div.desc"));

        for (int i = 0; i < nameList.size(); i++) {
            RestaurantEntity restaurant = new RestaurantEntity();

            System.out.println(nameList.get(i).getText());
            String name = nameList.get(i).getText().trim();
            restaurant.setName(name);

            // 주소 목록에서 올바른 요소를 선택
            if (i < addressList.size()) {
                System.out.println(addressList.get(i).getText());
                String address = addressList.get(i).getText().trim();
                restaurant.setAddress(address);
            }
        }


        driver.close();  // 크롬 창 닫기
        driver.quit();
    }









}
