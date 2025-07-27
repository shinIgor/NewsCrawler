package markigor.io.newscrawler.application.model.transfer.Response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.CrawlerSite;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

import java.util.Objects;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrawlerApiCreateResponse {
    Long crawlerApiId;
    NewsCrawlerApiSiteType site;
    String url;

    public static CrawlerApiCreateResponse of(CrawlerSite crawlerSite) {
        CrawlerApiCreateResponse result = new CrawlerApiCreateResponse();
        if (Objects.isNull(crawlerSite)) return result;

        result.crawlerApiId = crawlerSite.getId();
        result.site = NewsCrawlerApiSiteType.from(crawlerSite.getSiteName());
        result.url = crawlerSite.getUrl();

        return result;
    }
}
