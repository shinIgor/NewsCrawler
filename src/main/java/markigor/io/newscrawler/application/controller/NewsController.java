package markigor.io.newscrawler.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.transfer.Response.BaseResponse;
import markigor.io.newscrawler.application.service.NewsService;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news/v1/service")
public class NewsController {
    private final NewsService newsService;

    //TODO API 명세서
    // 1. GetMethod : 1. 뉴스 세팅 정보 가져 오기. 2. 개인 뉴스 데이터.
    // 2. PostMethod : 1. 뉴스 키워드 세팅. 2. 뉴스 사이트 세팅
    // 3. PutMethod : 1. 뉴스 키워드 변경. 2. 뉴스 사이트 변경...?!
    // 4. DeleteMethod : 1. 뉴스 키워드 삭제. 2. 뉴스 사이트 삭제


    @GetMapping("/setting")
    public BaseResponse<?> getSetting(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @GetMapping("/news")
    public BaseResponse<?> getNews(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @PostMapping("/setting/keyword")
    public BaseResponse<?> createNewsKeyword(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @PostMapping("/setting/site")
    public BaseResponse<?> createNewsSite(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @PutMapping("/setting/keyword")
    public BaseResponse<?> updateNewsKeyword(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @PutMapping("/setting/site")
    public BaseResponse<?> updateNewsSite(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @DeleteMapping("/setting/keyword")
    public BaseResponse<?> removeNewsKeyword(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

    @DeleteMapping("/setting/site")
    public BaseResponse<?> removeNewsSite(@NonNull @ValidCheck HttpServletRequest request) {
        return BaseResponse.success();
    }

}
