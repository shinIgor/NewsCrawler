package markigor.io.newscrawler.application.service;

import jakarta.servlet.http.HttpServletRequest;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerDataResponse;

import java.util.List;

public interface CrawlerService {
    //Note
    // 1. GetMethod : 1. 뉴스 세팅 정보 가져 오기. 2. 개인 뉴스 데이터.
    // 2. PostMethod : 1. 뉴스 키워드 세팅. 2. 뉴스 사이트 세팅
    // 3. PutMethod : 1. 뉴스 키워드 변경. 2. 뉴스 사이트 변경...?!
    // 4. DeleteMethod : 1. 뉴스 키워드 삭제. 2. 뉴스 사이트 삭제


    List<CrawlerDataResponse> getCrawlerSettings(HttpServletRequest request);

    void createCrawlerKeyword(HttpServletRequest request);

    void createCrawlerApi(HttpServletRequest request);

    void updateCrawlerKeyword(HttpServletRequest request);

    void updateCrawlerApi(HttpServletRequest request);

    void removeCrawlerKeyword(HttpServletRequest request);

    void removeCrawlerApi(HttpServletRequest request);

}
