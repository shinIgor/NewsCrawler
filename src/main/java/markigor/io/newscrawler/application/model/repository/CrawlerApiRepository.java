package markigor.io.newscrawler.application.model.repository;

import jakarta.validation.constraints.Size;
import java.util.Optional;
import markigor.io.newscrawler.application.model.entity.CrawlerSite;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface CrawlerApiRepository extends JpaRepository<CrawlerSite, Long>,
    QCrawlerApiRepository {

    Boolean existsCrawlerApiBySiteName(@Size(max = 20) String siteName);

    Optional<CrawlerSite> findById(Long id);
}
