package kr.nyamnyam.service.impl;

import kr.nyamnyam.model.entity.RestaurantEntity;
import kr.nyamnyam.service.ApiService;
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
    private final String API_URL = "http://openapi.seoul.go.kr:8088/6752776d616f796a38324674447771/xml/TbVwRestaurants/1/1000/";
    private final RestaurantService restaurantService;

    @Override
    public boolean getRestaurants() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(API_URL, String.class);

            //xml 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));


            // 필요한 데이터 추출
            NodeList nameList = doc.getElementsByTagName("POST_SJ");
            NodeList addressList = doc.getElementsByTagName("ADDRESS");
            NodeList newAddressList = doc.getElementsByTagName("NEW_ADDRESS");
            NodeList phoneNumberList = doc.getElementsByTagName("CMMN_TELNO");
            NodeList subwayInfoList = doc.getElementsByTagName("SUBWAY_INFO");

            List<RestaurantEntity> restaurantList = new ArrayList<>();

            for (int i = 0; i < nameList.getLength(); i++) {
                RestaurantEntity restaurant = new RestaurantEntity();
                restaurant.setName(nameList.item(i).getTextContent());
                restaurant.setAddress(addressList.item(i).getTextContent());
                restaurant.setNewAddress(newAddressList.item(i).getTextContent());
                restaurant.setPhoneNumber(phoneNumberList.item(i).getTextContent());
                restaurant.setSubwayInfo(subwayInfoList.item(i).getTextContent());

                restaurantList.add(restaurant);
            }

            // DB에 저장하고 성공 여부 반환
            return restaurantService.saveRestaurantFromApi(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
