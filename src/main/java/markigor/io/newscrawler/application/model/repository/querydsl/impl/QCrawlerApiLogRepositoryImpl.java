package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiLogRepository;

@RequiredArgsConstructor
public class QCrawlerApiLogRepositoryImpl implements QCrawlerApiLogRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
