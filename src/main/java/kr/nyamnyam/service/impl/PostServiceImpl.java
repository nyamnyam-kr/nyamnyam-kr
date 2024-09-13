package kr.nyamnyam.service.impl;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.PostTagRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostTagRepository postTagRepository;

    @Override
    public List<PostModel> findAllPerPage(int page) {
        Long totalCount = count();
        Pagination p = new Pagination(page, totalCount.intValue());
        return repository.findAll().stream()
                .skip(p.getStartRow()) // 페이지의 시작 행부터 스킵
                .limit(p.getEndRow() - p.getStartRow() + 1) // 페이지의 끝까지 제한
                .map(this::convertToModel) // 엔티티를 모델로 변환
                .collect(Collectors.toList()); // 리스트로 수집
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
                    .build();

            posts.add(post);
            repository.saveAll(posts);
            repository.findAll();
        }
        return repository.findAll().isEmpty();
    }

    @Override
    public List<PostModel> findAll() {
        return repository.findAll().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public PostModel findById(Long id) {
        return repository.findById(id)
                .map(this::convertToModel)
                .orElse(null);
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
        if (existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean save(PostModel model) {
        PostEntity entity = convertToEntity(model);
        return repository.save(entity) != null;
    }

    private PostModel convertToModel(PostEntity entity) {
        PostModel model = new PostModel();
        model.setId(entity.getId());
        model.setContent(entity.getContent());
        model.setTaste(entity.getTaste());
        model.setClean(entity.getClean());
        model.setService(entity.getService());
        model.setEntryDate(entity.getEntryDate());
        model.setModifyDate(entity.getModifyDate());
        model.setAverageRating((entity.getTaste() + entity.getClean() + entity.getService()) / 3.0);

        List<String> tags = postTagRepository.findByPostId(entity.getId()).stream()
                .map(postTagEntity -> postTagEntity.getTag().getName())
                .collect(Collectors.toList());
        model.setTags(tags);

        return model;
    }

    private PostEntity convertToEntity(PostModel model) {
        return PostEntity.builder()
                .content(model.getContent())
                .taste(model.getTaste())
                .clean(model.getClean())
                .service(model.getService())
                .build();
    }
}
