package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.Crawler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerRepository extends JpaRepository<Crawler, Long> {
}
