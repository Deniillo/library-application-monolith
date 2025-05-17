package itmo.deniill.service.services;

import itmo.deniill.dao.model.News;
import itmo.deniill.dao.model.enums.NewsType;
import itmo.deniill.dao.repository.NewsRepository;
import itmo.deniill.service.services.interfaces.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    @Autowired private final NewsRepository newsRepository;

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News findById(int id) {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("cant find news" + id));
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News update(int id, News newsDetails) {
        News news = findById(id);
        news.setHeadline(newsDetails.getHeadline());
        news.setDescription(newsDetails.getDescription());
        news.setPhotoUrl(newsDetails.getPhotoUrl());
        return newsRepository.save(news);
    }

    public List<News> getNewsByType(NewsType type) {
        return newsRepository.findByTypeOrderByCreatedDateDesc(type);
    }

    public void delete(int id) {
        News news = findById(id);
        newsRepository.delete(news);
    }
}
