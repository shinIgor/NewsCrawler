package markigor.io.newscrawler.application.model.repository.querydsl;

import markigor.io.newscrawler.application.model.entity.UserService;

import java.util.List;

public interface QUserServiceRepository {
    List<UserService> getUserServiceList(Long userId);
}
