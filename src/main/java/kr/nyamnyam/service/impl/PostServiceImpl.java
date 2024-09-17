package kr.nyamnyam.service.impl;

import jakarta.transaction.Transactional;
import kr.nyamnyam.model.domain.ImageModel;
import kr.nyamnyam.model.domain.PostModel;
import kr.nyamnyam.model.entity.PostEntity;
import kr.nyamnyam.model.entity.PostTagEntity;
import kr.nyamnyam.model.entity.TagEntity;
import kr.nyamnyam.model.repository.PostRepository;
import kr.nyamnyam.model.repository.PostTagRepository;
import kr.nyamnyam.model.repository.TagRepository;
import kr.nyamnyam.pattern.proxy.Pagination;
import kr.nyamnyam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    @Override
    public PostModel postWithImage(Long id){
        PostEntity postEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        List<ImageModel> imageModels = postEntity.getImages().stream()
                .map(imageEntity -> ImageModel.builder()
                        .originalFilename(imageEntity.getOriginalFileName())
                        .storedFileName(imageEntity.getStoredFileName())
                        .build())
                .collect(Collectors.toList());

        PostModel postModel = convertToModel(postEntity);
        postModel.setImages(imageModels);

        return postModel;
    }

    @Override
    public PostEntity findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    @Override
    public List<PostModel> findAllPerPage(int page) {
        Long totalCount = count();
        Pagination p = new Pagination(page, totalCount.intValue());
        return repository.findAll().stream()
                .skip(p.getStartRow())
                .limit(p.getEndRow() - p.getStartRow() + 1)
                .map(this::convertToModel)
                .collect(Collectors.toList());
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
            postTagRepository.deleteByPostId(id);
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Long createPost(PostModel model) {
        PostEntity entity = convertToEntity(model);
        entity.setEntryDate(LocalDateTime.now());
        repository.save(entity);

        saveTags(model.getTags(), entity);

        return entity.getId();
    }

    @Override
    public Boolean updatePost(Long id, PostModel model) {
        PostEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        PostEntity updatedEntity = existingEntity.toBuilder()
                .content(model.getContent())
                .taste(model.getTaste())
                .clean(model.getClean())
                .service(model.getService())
                .modifyDate(LocalDateTime.now())
                .build();

        repository.save(updatedEntity);
        saveTags(model.getTags(), existingEntity);

        return true;
    }

    // 태그 저장
    private void saveTags(List<String> tags, PostEntity postEntity) {
        if (tags != null && !tags.isEmpty()) {
            List<PostTagEntity> postTags = tags.stream()
                    .map(tagName -> {
                        TagEntity tag = tagRepository.findByName(tagName)
                                .orElseGet(() -> {
                                    TagEntity newTag = new TagEntity();
                                    newTag.setName(tagName);
                                    tagRepository.save(newTag);
                                    return newTag;
                                });
                        return new PostTagEntity(postEntity, tag);
                    })
                    .collect(Collectors.toList());
            postTagRepository.saveAll(postTags);
        }
    }

    private PostModel convertToModel(PostEntity entity) {
        return PostModel.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .taste(entity.getTaste())
                .clean(entity.getClean())
                .service(entity.getService())
                .entryDate(entity.getEntryDate())
                .modifyDate(entity.getModifyDate())
                .averageRating((entity.getTaste() + entity.getClean() + entity.getService()) / 3.0)
                .tags(postTagRepository.findByPostId(entity.getId()).stream()
                        .map(postTagEntity -> postTagEntity.getTag().getName())
                        .collect(Collectors.toList()))
                .build();
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
