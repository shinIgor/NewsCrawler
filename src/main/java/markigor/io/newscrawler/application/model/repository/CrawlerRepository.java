package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerRepository extends JpaRepository<Crawler, Long>, QCrawlerRepository {
}
