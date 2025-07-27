package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.CrawlerSiteLog;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiLogRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface CrawlerApiLogRepository extends JpaRepository<CrawlerSiteLog, Long>,
    QCrawlerApiLogRepository {

}
