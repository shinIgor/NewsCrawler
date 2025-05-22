package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerRepository;
import markigor.io.newscrawler.application.util.ValidCheck;

import java.util.List;

import static markigor.io.newscrawler.application.model.entity.QCrawler.crawler;
import static markigor.io.newscrawler.application.model.entity.QUserService.userService;

@RequiredArgsConstructor
public class QCrawlerRepositoryImpl implements QCrawlerRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @ValidCheck
    public List<Crawler> getCrawlerList(Long accountId) {
        return jpaQueryFactory.select(crawler)
                .from(crawler)
                .join(crawler.userService)
                .where(userService.accountId.eq(accountId))
                .fetch();
    }
}
