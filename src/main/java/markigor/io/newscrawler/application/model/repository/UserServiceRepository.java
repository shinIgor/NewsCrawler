package markigor.io.newscrawler.application.model.repository;

import markigor.io.newscrawler.application.model.entity.UserService;
import markigor.io.newscrawler.application.model.repository.querydsl.QUserServiceRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface UserServiceRepository extends JpaRepository<UserService, Long>,
    QUserServiceRepository {

}
