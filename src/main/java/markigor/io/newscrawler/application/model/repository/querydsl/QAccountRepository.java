package markigor.io.newscrawler.application.model.repository.querydsl;

import markigor.io.newscrawler.application.model.entity.Account;

public interface QAccountRepository {

    Account getAccount(String userId);
}
