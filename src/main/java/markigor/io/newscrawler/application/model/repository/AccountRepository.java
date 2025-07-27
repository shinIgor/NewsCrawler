package markigor.io.newscrawler.application.model.repository;

import jakarta.validation.constraints.Size;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.repository.querydsl.QAccountRepository;
import markigor.io.newscrawler.configuration.datasource.NewsCrawlerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NewsCrawlerDataSource
public interface AccountRepository extends JpaRepository<Account, Long>, QAccountRepository {

    Boolean existsAccountByUserId(@Size(max = 20) String userId);

}
