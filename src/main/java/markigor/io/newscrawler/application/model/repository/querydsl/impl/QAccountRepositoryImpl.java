package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.repository.querydsl.QAccountRepository;
import markigor.io.newscrawler.application.util.ValidCheck;

import static markigor.io.newscrawler.application.model.entity.QAccount.account;

@RequiredArgsConstructor
public class QAccountRepositoryImpl implements QAccountRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @ValidCheck
    public Account getAccount(String userId) {
        return jpaQueryFactory.select(account)
                .from(account)
                .where(account.userId.eq(userId))
                .fetchFirst();
    }
}
