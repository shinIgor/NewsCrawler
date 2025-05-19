package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerRepository;

@RequiredArgsConstructor
public class QCrawlerRepositoryImpl implements QCrawlerRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
