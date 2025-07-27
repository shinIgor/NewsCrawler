package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface CrawlerRepository extends JpaRepository<Crawler, Long>, QCrawlerRepository {

}
