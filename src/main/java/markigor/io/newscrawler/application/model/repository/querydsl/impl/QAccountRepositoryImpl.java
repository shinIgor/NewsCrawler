package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QAccountRepository;

@RequiredArgsConstructor
public class QAccountRepositoryImpl implements QAccountRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
