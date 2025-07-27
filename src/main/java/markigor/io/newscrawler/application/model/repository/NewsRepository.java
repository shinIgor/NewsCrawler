package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.News;
import markigor.io.newscrawler.application.model.repository.querydsl.QNewsRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface NewsRepository extends JpaRepository<News, Long>, QNewsRepository {

}
