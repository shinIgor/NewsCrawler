package markigor.io.newscrawler.application.model.transfer.Response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.News;
import markigor.io.newscrawler.application.model.type.NewsCrawlerApiSiteType;

import java.util.Objects;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsServiceResponse {
    String newsSite;

    String newsTitle;

    String newsPost;

    public static NewsServiceResponse of(News news) {
        NewsServiceResponse result = new NewsServiceResponse();
        if (Objects.isNull(news)) return result;

        result.newsSite = NewsCrawlerApiSiteType.fromCode(news.getCrawlerApiId()).name();
        result.newsTitle = news.getNewsTitle();
        result.newsPost = news.getNewsPost();
        return result;
    }
}
