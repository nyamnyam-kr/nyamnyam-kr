package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.ApiService;
import kr.nyamnyam.service.CategoryService;
import kr.nyamnyam.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final String API_URL = "http://openapi.seoul.go.kr:8088/6752776d616f796a38324674447771/xml/TbVwRestaurants/1/500/";
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;


    @Override
    public List<RestaurantEntity> getRestaurants() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(API_URL, String.class);

            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));


            NodeList idList = doc.getElementsByTagName("POST_SN");
            NodeList langCodeList = doc.getElementsByTagName("LANG_CODE_ID");
            NodeList nameList = doc.getElementsByTagName("POST_SJ");
            NodeList addressList = doc.getElementsByTagName("NEW_ADDRESS");
            NodeList phoneNumberList = doc.getElementsByTagName("CMMN_TELNO");
            NodeList websiteUrlList = doc.getElementsByTagName("CMMN_HMPG_URL");
            NodeList useTimeList = doc.getElementsByTagName("CMMN_USE_TIME");
            NodeList subwayInfoList = doc.getElementsByTagName("SUBWAY_INFO");
            NodeList representativeMenuList = doc.getElementsByTagName("FD_REPRSNT_MENU");

            List<RestaurantEntity> restaurantList = new ArrayList<>();

            for (int i = 0; i < nameList.getLength(); i++) {
                // 한국어만 추출
                String langCode = langCodeList.item(i).getTextContent();
                if ("ko".equals(langCode)) {
                    RestaurantEntity restaurant = new RestaurantEntity();
                    restaurant.setPostId(Long.valueOf(idList.item(i).getTextContent()));
                    restaurant.setLangCodeId(doc.getElementsByTagName("LANG_CODE_ID").item(i).getTextContent());
                    restaurant.setName(nameList.item(i).getTextContent());
                    restaurant.setPostUrl(doc.getElementsByTagName("POST_URL").item(i).getTextContent());
                    restaurant.setAddress(addressList.item(i).getTextContent());
                    restaurant.setPhoneNumber(phoneNumberList.item(i).getTextContent());
                    restaurant.setWebsiteUrl(websiteUrlList.item(i).getTextContent());
                    restaurant.setUseTime(useTimeList.item(i).getTextContent());
                    restaurant.setSubwayInfo(subwayInfoList.item(i).getTextContent());
                    restaurant.setRepresentativeMenu(representativeMenuList.item(i).getTextContent());


                    String representativeMenu = restaurant.getRepresentativeMenu();
                    restaurant.setCategory(categoryService.extractCategory(representativeMenu));

         /*           String imageUrl = restImgService.extractImageUrl(restaurant.getPostUrl());
                    restaurant.setImageUrl(imageUrl);*/

                    if (restaurantService.existsByNameAndAddress(restaurant.getName(), restaurant.getAddress())) {
                        restaurantList.add(restaurant);
                    }
                }
            }

            restaurantService.saveRestaurantFromApi(restaurantList);
            return restaurantList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }


}