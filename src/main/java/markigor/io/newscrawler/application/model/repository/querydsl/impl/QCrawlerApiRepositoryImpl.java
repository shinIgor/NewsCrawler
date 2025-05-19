package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QCrawlerApiRepository;

@RequiredArgsConstructor
public class QCrawlerApiRepositoryImpl implements QCrawlerApiRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
