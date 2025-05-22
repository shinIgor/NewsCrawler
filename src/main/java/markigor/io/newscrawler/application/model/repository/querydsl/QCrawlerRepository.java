package markigor.io.newscrawler.application.model.repository.querydsl;

import markigor.io.newscrawler.application.model.entity.Crawler;

import java.util.List;

public interface QCrawlerRepository {
    List<Crawler> getCrawlerList(Long accountId);
}
