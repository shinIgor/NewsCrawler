package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.repository.querydsl.QNewsRepository;

@RequiredArgsConstructor
public class QNewsRepositoryImpl implements QNewsRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
