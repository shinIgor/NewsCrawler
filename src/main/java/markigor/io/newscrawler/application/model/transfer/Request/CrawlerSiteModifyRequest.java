package markigor.io.newscrawler.application.model.transfer.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrawlerSiteModifyRequest {
    NewsCrawlerApiSiteType siteName;
    String url;
    Map<String, Object> requestHeader;
    Map<String, Object> requestBody;

}
