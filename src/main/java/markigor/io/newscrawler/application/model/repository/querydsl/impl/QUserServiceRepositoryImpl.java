package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QUserServiceRepository;

@RequiredArgsConstructor
public class QUserServiceRepositoryImpl implements QUserServiceRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
