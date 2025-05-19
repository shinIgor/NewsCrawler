package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerLogRepository;

@RequiredArgsConstructor
public class QCrawlerLogRepositoryImpl implements QCrawlerLogRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
