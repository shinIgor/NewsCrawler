package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.News;
import markigor.io.newscrawler.application.model.repository.querydsl.QNewsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, QNewsRepository {

}
