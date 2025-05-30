package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.CrawlerApi;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerApiRepository extends JpaRepository<CrawlerApi, Long>, QCrawlerApiRepository {
}
