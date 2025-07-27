package markigor.io.newscrawler.application.model.repository.querydsl;

import java.util.List;
import markigor.io.newscrawler.application.model.entity.Crawler;

public interface QCrawlerRepository {

    List<Crawler> getCrawlerList(Long accountId);

    Crawler getCrawlerByKeyword(String keyword);

    Crawler getCrawlerByKeyword(String keyword, Long duration);

}
