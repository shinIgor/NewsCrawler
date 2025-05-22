package markigor.io.newscrawler.application.model.repository.querydsl;

import markigor.io.newscrawler.application.model.entity.News;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QNewsRepository {
    List<News> getNewsList(Pageable pageable, List<Long> crawlerIdList);
}
