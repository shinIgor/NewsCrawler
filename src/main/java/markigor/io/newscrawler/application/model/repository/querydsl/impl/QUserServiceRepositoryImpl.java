package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.UserService;
import markigor.io.newscrawler.application.model.repository.querydsl.QUserServiceRepository;
import markigor.io.newscrawler.application.util.ValidCheck;

import java.util.List;

import static markigor.io.newscrawler.application.model.entity.QUserService.userService;

@RequiredArgsConstructor
public class QUserServiceRepositoryImpl implements QUserServiceRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @ValidCheck
    public List<UserService> getUserServiceList(Long userId) {
        return jpaQueryFactory.select(userService)
                .from(userService)
                .where(userService.accountId.eq(userId))
                .fetch();
    }
}
