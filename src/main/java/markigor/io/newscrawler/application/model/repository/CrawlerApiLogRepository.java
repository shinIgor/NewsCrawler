package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.CrawlerApiLog;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerApiLogRepository extends JpaRepository<CrawlerApiLog, Long>, QCrawlerApiLogRepository {
}
