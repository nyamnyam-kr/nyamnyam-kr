package kr.nyamnyam.service.impl;


import kr.nyamnyam.model.entity.ChannelEntity;
import kr.nyamnyam.model.repository.ChannelRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.ChannelService;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final Pagination pagination;

    @Override
    public ChannelEntity save(ChannelEntity channelEntity) {

        return channelRepository.save(channelEntity);
    }

    @Override
    public List<ChannelEntity> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<ChannelEntity> findAllPerPage(int page) {
        List<ChannelEntity> list = channelRepository.findAll();
        int totalCount = (int) count();
        Pagination p=new Pagination(totalCount,page);
        int startRow = p.getStartRow();
        int endRow = p.getEndRow();
        List<ChannelEntity> pageData = new ArrayList<>();

        for (int i = startRow; i <= endRow && i< list.size(); i++) {
            ChannelEntity channelEntity = list.get(i);
            pageData.add(channelEntity);
        }

        return pageData;
    }

    @Override
    public Optional<ChannelEntity> findById(String id) {
        return channelRepository.findById(id);
    }

    @Override
    public boolean deleteById(String id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(String id) {
        return channelRepository.existsById(id);
    }

    @Override
    public long count() {
        return channelRepository.count();
    }

    @Override
    public List<ChannelEntity> crawling() {
        System.out.println("크롤링 실행!!! 너무 어려움");


        String url = "https://serieson.naver.com/v3/movie/ranking/realtime";
        Document doc = null;

        try {
            // 네이버 영화 페이지로 이동 크롤링할 사이트를 넣어
            doc = Jsoup.connect(url).get();
            Elements name = doc.select("span.Title_title__s9o0D");
            Elements rank = doc.select("#container > div.RankingPage_ranking_wrap__GB855 > ol > li:nth-child(1) > a > div.RankingNumber_ranking_area__p8B_q > em > span");

            List<ChannelEntity> channelList = new ArrayList<>();

            for (Element element : name) {
                String storedname = element.text().trim();

                ChannelEntity channelEntity = ChannelEntity.builder()
                        .name(storedname)
                        .build();
                channelList.add(channelEntity);

            }

            channelRepository.saveAll(channelList);

            return channelList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}



