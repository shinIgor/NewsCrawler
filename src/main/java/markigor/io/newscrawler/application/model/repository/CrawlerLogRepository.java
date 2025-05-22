package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.CrawlerLog;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Long>, QCrawlerLogRepository {
}
