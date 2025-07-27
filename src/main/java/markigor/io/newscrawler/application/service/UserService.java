package markigor.io.newscrawler.application.service;

import jakarta.servlet.http.HttpServletRequest;
import markigor.io.newscrawler.application.model.transfer.Request.UserServiceCreateRequest;
import markigor.io.newscrawler.application.model.transfer.Response.UserServiceCreateResponse;

public interface UserService {

    // Note 유저의 Crawler 데이터 추가 기능인데... 이거는 관리자용 아닐까?
    UserServiceCreateResponse createCrawlerKeyword(HttpServletRequest request,
        UserServiceCreateRequest param);

}
