package itmo.deniill.dao.repository;

import itmo.deniill.dao.model.News;
import itmo.deniill.dao.model.enums.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByTypeOrderByCreatedDateDesc(NewsType type);
}
