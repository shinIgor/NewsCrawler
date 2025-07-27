package markigor.io.newscrawler.application.model.repository.querydsl;

import markigor.io.newscrawler.application.model.entity.CrawlerSite;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

public interface QCrawlerApiRepository {
    CrawlerSite getCrawlerBySite(NewsCrawlerApiSiteType type);
}
