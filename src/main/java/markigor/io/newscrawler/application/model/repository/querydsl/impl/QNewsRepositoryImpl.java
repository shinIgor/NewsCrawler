package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.News;
import markigor.io.newscrawler.application.model.repository.querydsl.QNewsRepository;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static markigor.io.newscrawler.application.model.entity.QNews.news;

@RequiredArgsConstructor
public class QNewsRepositoryImpl implements QNewsRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    @ValidCheck
    public List<News> getNewsList(Pageable pageable, List<Long> crawlerIdList) {
        return jpaQueryFactory.select(news)
                .from(news)
                .where(news.crawlerId.in(crawlerIdList))
                .orderBy(news.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
