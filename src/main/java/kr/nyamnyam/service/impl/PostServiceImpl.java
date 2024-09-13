package kr.nyamnyam.service.impl;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Override
    public List<PostEntity> findAllPerPage(int page) {
        List<PostEntity> ls = findAll();
        Long totalCount = count();
        Pagination p = new Pagination(page, totalCount.intValue());
        int startRow = p.getStartRow();
        int endRow = p.getEndRow();

        List<PostEntity> pageData = new ArrayList<>();

        for (int i = startRow; i <= endRow && i < ls.size(); i++) {
            pageData.add(ls.get(i));
        }
        return pageData;
    }


    @Transactional
    @Override
    public Boolean crawling() {
        String url = "http://www.cgv.co.kr/movies/";
        Document doc = null;
        List<PostEntity> posts = new ArrayList<>();

        try {
            doc = Jsoup.connect(url).timeout(5000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null) {
            System.out.println("문서를 로드할 수 없습니다.");
        }

        Elements elements = doc.select("div.sect-movie-chart");
        Iterator<Element> rank = elements.select("strong.rank").iterator();
        Iterator<Element> title = elements.select("strong.title").iterator();

        while (rank.hasNext() && title.hasNext()) {
            String movieRank = rank.next().text().replaceAll("[^0-9]", "");
            String movieTitle = title.next().text();
            System.out.println(movieRank + ": " + movieTitle);

            PostEntity post = PostEntity.builder()
                    .content("Rank: " + movieRank + ", Title: " + movieTitle)
                    .taste(Long.parseLong(movieRank)) // 맛을 순위로 대체, 예시로 사용
                    .clean(5L) // 예시 값
                    .service(5L) // 예시 값
                    .entryDate(new Date())
                    .modifyDate(new Date())
                    .build();

            posts.add(post);
            repository.saveAll(posts);
            repository.findAll();
        }
        return repository.findAll().isEmpty();
    }

    @Override
    public List<PostEntity> findAll() {
        var p1 = new HashMap<String, String>();
        p1 = new HashMap<String, String>();

        return repository.findAll();
    }

    @Override
    public PostEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        return false;
    }

    @Override
    public Boolean save(PostEntity entity) {
        PostEntity post = PostEntity.builder()
                .content("Some content")
                .taste(10L) // 예제 값
                .clean(10L) // 예제 값
                .service(10L) // 예제 값
                .entryDate(new Date())
                .modifyDate(new Date())
                .build();

        return repository.save(post) != null;
    }
}
