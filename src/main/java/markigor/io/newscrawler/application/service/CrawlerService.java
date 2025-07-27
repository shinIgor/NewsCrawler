package markigor.io.newscrawler.application.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import markigor.io.newscrawler.application.model.transfer.Request.CrawlerModifyRequest;
import markigor.io.newscrawler.application.model.transfer.Request.CrawlerSiteCreateRequest;
import markigor.io.newscrawler.application.model.transfer.Request.CrawlerSiteModifyRequest;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerApiCreateResponse;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerApiModifyResponse;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerDataResponse;

public interface CrawlerService {
    //Note
    // 1. GetMethod : 1. 뉴스 세팅 정보 가져 오기. 2. 개인 뉴스 데이터.
    // 2. PostMethod : 1. 뉴스 키워드 세팅. 2. 뉴스 사이트 세팅
    // 3. PutMethod : 1. 뉴스 키워드 변경. 2. 뉴스 사이트 변경...?!
    // 4. DeleteMethod : 1. 뉴스 키워드 삭제. 2. 뉴스 사이트 삭제

    //Note 유저용 Crawler Setting Service
    List<CrawlerDataResponse> getCrawlerSettings(HttpServletRequest request);


    //Note
    // Crawler Keyword 변경은 관리자 권한..?!
    // 유저는 UserService 로만 관리
    void modifyCrawlerKeyword(HttpServletRequest request, CrawlerModifyRequest param);

    void removeCrawlerKeyword(HttpServletRequest request, Long crawlerId);

    // Note Crawler Site 설정 Service

    CrawlerApiCreateResponse createCrawlerSite(HttpServletRequest request,
        CrawlerSiteCreateRequest param);

    CrawlerApiModifyResponse modifyCrawlerSite(HttpServletRequest request,
        CrawlerSiteModifyRequest param);

    void removeCrawlerSite(HttpServletRequest request, Long crawlerApiId);

}
