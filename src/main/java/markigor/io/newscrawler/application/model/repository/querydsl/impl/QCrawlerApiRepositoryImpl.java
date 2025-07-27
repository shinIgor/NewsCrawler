package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import static markigor.io.newscrawler.application.model.entity.QCrawlerSite.crawlerSite;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.CrawlerSite;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiRepository;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;
import markigor.io.newscrawler.application.util.ValidCheck;


@RequiredArgsConstructor
public class QCrawlerApiRepositoryImpl implements QCrawlerApiRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @ValidCheck
    public CrawlerSite getCrawlerBySite(NewsCrawlerApiSiteType type) {
        return jpaQueryFactory.selectFrom(crawlerSite)
            .where(crawlerSite.siteName.eq(type.getSite()))
            .fetchFirst();
    }
}
