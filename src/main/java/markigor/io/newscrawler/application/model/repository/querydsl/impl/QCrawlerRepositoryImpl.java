package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import static markigor.io.newscrawler.application.model.entity.QCrawler.crawler;
import static markigor.io.newscrawler.application.model.entity.QUserService.userService;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerRepository;
import markigor.io.newscrawler.application.util.ValidCheck;

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

    @Override
    @ValidCheck
    public Crawler getCrawlerByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(crawler)
            .where(crawler.keyWord.eq(keyword))
            .fetchFirst();
    }

    @Override
    @ValidCheck
    public Crawler getCrawlerByKeyword(String keyword, Long duration) {
        return jpaQueryFactory.selectFrom(crawler)
            .where(crawler.keyWord.eq(keyword))
            .where(crawler.duration.eq(duration))
            .fetchFirst();
    }


}
