package itmo.deniill.service.services.interfaces;


import itmo.deniill.dao.model.News;
import itmo.deniill.dao.model.enums.NewsType;
import itmo.deniill.exception.NewsNotFoundException;

import java.util.List;

public interface NewsService {

    List<News> getNewsByType(NewsType type);

    List<News> findAll();

    News findById(int id) throws NewsNotFoundException;

    News save(News news);

    News update(int id, News newsDetails) throws NewsNotFoundException;

    void delete(int id) throws NewsNotFoundException;
}
