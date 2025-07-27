package markigor.io.newscrawler.application.model.transfer.Response;


import java.util.Objects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrawlerDataResponse {

    Long Id;
    String NewsSite;
    String keyWord;
    Long duration;
    Boolean isScrap;

    public static CrawlerDataResponse of(Crawler data) {
        CrawlerDataResponse result = new CrawlerDataResponse();
        if (Objects.isNull(data)) {
            return result;
        }

        result.Id = data.getId();
        result.NewsSite = NewsCrawlerApiSiteType.fromCode(data.getCrawlerSiteId()).name();
        result.keyWord = data.getKeyWord();
        result.duration = data.getDuration();
        result.isScrap = data.getIsScrap();
        return result;
    }
}
