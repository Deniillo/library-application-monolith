package itmo.deniill.service.services;

import itmo.deniill.dao.model.News;
import itmo.deniill.exception.NewsNotFoundException;
import itmo.deniill.service.services.interfaces.NewsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NewsServiceImpl implements NewsService {

    private final List<News> newsList = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<News> findAll() {
        return new ArrayList<>(newsList);
    }

    public News findById(int id) {
        return newsList.stream()
                .filter(news -> news.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NewsNotFoundException("News not found with id: " + id));
    }

    public News save(News news) {
        news.setId(idCounter.getAndIncrement());
        newsList.add(news);
        return news;
    }

    public News update(int id, News newsDetails) {
        News existingNews = findById(id);
        existingNews.setHeadline(newsDetails.getHeadline());
        existingNews.setDescription(newsDetails.getDescription());
        existingNews.setPhotoUrl(newsDetails.getPhotoUrl());
        return existingNews;
    }

    public void delete(int id) {
        News news = findById(id);
        newsList.remove(news);
    }
}
