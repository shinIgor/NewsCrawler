package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.CrawlerLog;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerLogRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Long>,
    QCrawlerLogRepository {

}
