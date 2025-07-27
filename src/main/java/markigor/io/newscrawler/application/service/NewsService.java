package markigor.io.newscrawler.application.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import markigor.io.newscrawler.application.model.transfer.Request.BasePageRequest;
import markigor.io.newscrawler.application.model.transfer.Response.NewsServiceResponse;

public interface NewsService {

    List<NewsServiceResponse> getNewsData(HttpServletRequest request, BasePageRequest pageRequest);
}
