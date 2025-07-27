package markigor.io.newscrawler.application.model.transfer.Response;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.entity.UserService;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceCreateResponse {

    Long crawlerId;
    Long userServiceId;
    NewsCrawlerApiSiteType newsSite;
    String keyword;
    Long duration;

    public static UserServiceCreateResponse of(UserService userService, Crawler crawler) {
        UserServiceCreateResponse result = new UserServiceCreateResponse();
        if (Objects.isNull(userService) || Objects.isNull(crawler)) {
            return result;
        }

        result.crawlerId = crawler.getId();
        result.userServiceId = userService.getId();
        result.newsSite = NewsCrawlerApiSiteType.fromCode(crawler.getCrawlerSiteId());
        result.keyword = crawler.getKeyWord();
        result.duration = crawler.getDuration();

        return result;
    }
}
