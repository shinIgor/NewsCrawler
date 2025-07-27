package markigor.io.newscrawler.application.model.repository.querydsl;

import java.util.List;
import markigor.io.newscrawler.application.model.entity.UserService;

public interface QUserServiceRepository {

    List<UserService> getUserServiceList(Long userId);

    UserService getUserServiceByCrawler(Long crawlerId);

    UserService getUserServiceByUserIdAndCrawlerId(Long userId, Long crawlerId);
}
