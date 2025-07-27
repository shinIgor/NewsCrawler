package markigor.io.newscrawler.application.model.transfer.Response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.CrawlerSite;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

import java.util.Map;
import java.util.Objects;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrawlerApiModifyResponse {
    Long crawlerApiId;
    NewsCrawlerApiSiteType site;
    Map<String, Object> requestHeader;
    String url;

    public static CrawlerApiModifyResponse of(CrawlerSite crawlerSite) {
        CrawlerApiModifyResponse result = new CrawlerApiModifyResponse();
        if (Objects.isNull(crawlerSite)) return result;

        result.crawlerApiId = crawlerSite.getId();
        result.site = NewsCrawlerApiSiteType.from(crawlerSite.getSiteName());
        result.url = crawlerSite.getUrl();
        result.requestHeader = crawlerSite.getHeader();

        return result;
    }
}
