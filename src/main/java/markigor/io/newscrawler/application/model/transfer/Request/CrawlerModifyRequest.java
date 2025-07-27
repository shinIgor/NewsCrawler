package markigor.io.newscrawler.application.model.transfer.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrawlerModifyRequest {
    Long crawlerId;
    NewsCrawlerApiSiteType newsSite;
    String keyword;
    Long duration;
}
