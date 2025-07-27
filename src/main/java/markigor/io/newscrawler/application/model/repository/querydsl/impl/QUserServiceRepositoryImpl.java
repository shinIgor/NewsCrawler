package markigor.io.newscrawler.application.model.repository.querydsl.impl;

import static markigor.io.newscrawler.application.model.entity.QUserService.userService;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.entity.UserService;
import markigor.io.newscrawler.application.model.repository.querydsl.QUserServiceRepository;
import markigor.io.newscrawler.application.util.ValidCheck;

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

    @Override
    @ValidCheck
    public UserService getUserServiceByCrawler(Long crawlerId) {
        return jpaQueryFactory.selectFrom(userService)
            .where(userService.crawlerId.eq(crawlerId))
            .fetchFirst();
    }

    @Override
    @ValidCheck
    public UserService getUserServiceByUserIdAndCrawlerId(Long userId, Long crawlerId) {
        return null;
    }
}
