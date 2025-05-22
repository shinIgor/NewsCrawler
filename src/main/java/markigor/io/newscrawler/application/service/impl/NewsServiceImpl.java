package markigor.io.newscrawler.application.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.define.RedisCacheKeyDefine;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.entity.UserService;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.application.model.repository.AccountRepository;
import markigor.io.newscrawler.application.model.repository.CrawlerRepository;
import markigor.io.newscrawler.application.model.repository.NewsRepository;
import markigor.io.newscrawler.application.model.repository.UserServiceRepository;
import markigor.io.newscrawler.application.model.transfer.Dto.SessionTokenDto;
import markigor.io.newscrawler.application.model.transfer.Request.BasePageRequest;
import markigor.io.newscrawler.application.model.transfer.Response.NewsServiceResponse;
import markigor.io.newscrawler.application.model.type.AdditionalInformation;
import markigor.io.newscrawler.application.service.NewsService;
import markigor.io.newscrawler.application.service.RedisCacheService;
import markigor.io.newscrawler.application.util.HttpHeaderUtil;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final RedisCacheService redisCacheService;
    private final AccountRepository accountRepository;
    private final NewsRepository newsRepository;
    private final CrawlerRepository crawlerRepository;
    private final UserServiceRepository userServiceRepository;

    @Override
    @ValidCheck
    public List<NewsServiceResponse> getNewsData(HttpServletRequest request, BasePageRequest pageRequest) {

        //Note API User Session 체크
        String accessToken = HttpHeaderUtil.getExtractBearerToken(request);
        SessionTokenDto sessionTokenDto = redisCacheService.getValue(RedisCacheKeyDefine.getAccessTokenKey(accessToken), SessionTokenDto.class);
        if (Objects.isNull(sessionTokenDto)) {
            log.error("Not found session. accessToken: {}", accessToken);
            throw new CommonException(CommonErrorMessage.INVALID_ACCESS_TOKEN);
        }

        Pageable pageable = pageRequest.makePageable();

        //Note 유저 Account_ID 기준으로 해당 유저 데이터 정보 조회. (Session을 받았지만 DB에 데이터 없는걸 방지하기 위함)
        Map<String, Object> additional = sessionTokenDto.getAdditionalInformation();

        Long accountId = (Long) additional.getOrDefault(AdditionalInformation.USN.getName(), null);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Not found account Id. USN: {}", accountId);
                    return new CommonException(CommonErrorMessage.UNKNOWN_USN);
                });


        // Note UserService:CrawlerId -> News 데이터 가져오기.
        List<Long> crawlerIdList = userServiceRepository.getUserServiceList(accountId)
                .stream()
                .map(UserService::getCrawlerId)
                .toList();

        // Note NewsData -> Response
        List<NewsServiceResponse> result = newsRepository.getNewsList(pageable, crawlerIdList)
                .stream()
                .map(NewsServiceResponse::of)
                .toList();

        return result;
    }
}
