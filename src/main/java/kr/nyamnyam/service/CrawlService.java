package kr.nyamnyam.service;

import kr.nyamnyam.model.entity.CrawlingInfo;
import kr.nyamnyam.model.entity.RestaurantEntity;

import java.util.List;

public interface CrawlService {

    void crawlAndSaveInfos();

    List<CrawlingInfo> getCrawlingInfos();

    int getTotalCount();

    List<CrawlingInfo> getCrawlingInfos(int startRow, int endRow);
}
