package markigor.io.newscrawler.application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.entity.CrawlerSite;
import markigor.io.newscrawler.application.model.entity.CrawlerSiteLog;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.application.model.repository.AccountRepository;
import markigor.io.newscrawler.application.model.repository.CrawlerApiLogRepository;
import markigor.io.newscrawler.application.model.repository.CrawlerApiRepository;
import markigor.io.newscrawler.application.model.repository.CrawlerLogRepository;
import markigor.io.newscrawler.application.model.repository.CrawlerRepository;
import markigor.io.newscrawler.application.model.repository.UserServiceRepository;
import markigor.io.newscrawler.application.model.transfer.Request.CrawlerModifyRequest;
import markigor.io.newscrawler.application.model.transfer.Request.CrawlerSiteCreateRequest;
import markigor.io.newscrawler.application.model.transfer.Request.CrawlerSiteModifyRequest;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerApiCreateResponse;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerApiModifyResponse;
import markigor.io.newscrawler.application.model.transfer.Response.CrawlerDataResponse;
import markigor.io.newscrawler.application.model.type.AccountRoleType;
import markigor.io.newscrawler.application.service.CrawlerService;
import markigor.io.newscrawler.application.service.RedisCacheService;
import markigor.io.newscrawler.application.util.SessionUtil;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlerServiceImpl implements CrawlerService {

    private final RedisCacheService redisCacheService;
    private final CrawlerRepository crawlerRepository;
    private final CrawlerApiRepository crawlerApiRepository;
    private final UserServiceRepository userServiceRepository;
    private final AccountRepository accountRepository;

    private final CrawlerLogRepository crawlerLogRepository;
    private final CrawlerApiLogRepository crawlerApiLogRepository;

    @Override
    @ValidCheck
    public List<CrawlerDataResponse> getCrawlerSettings(HttpServletRequest request) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        List<CrawlerDataResponse> crawlerDataList = crawlerRepository.getCrawlerList(
                account.getId())
            .stream()
            .map(CrawlerDataResponse::of)
            .toList();

        return crawlerDataList;
    }


    @Override
    @ValidCheck
    public CrawlerApiCreateResponse createCrawlerSite(HttpServletRequest request,
        CrawlerSiteCreateRequest param) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        //Note CrawlerApi의 경우 Admin 작업.
        if (!account.getAccountRole().equals(AccountRoleType.ADMIN)) {
            log.error("CrawlerAPI Setting is Only Admin Role. account_id: {}, name: {}",
                account.getUserId(), account.getUserName());
            throw new CommonException(CommonErrorMessage.FORBIDDEN);
        }

        Boolean isExist = crawlerApiRepository.existsCrawlerApiBySiteName(
            param.getSiteName().name());
        if (isExist) {
            log.error("Duplicate create request. site_name:{}, url:{}", param.getSiteName(),
                param.getUrl());
            throw new CommonException(CommonErrorMessage.DUPLICATE_REQUEST);
        }

        CrawlerSite result = crawlerApiRepository.save(
            CrawlerSite.builder()
                .siteName(param.getSiteName().name())
                .url(param.getUrl())
                .header(param.getRequestHeader())
                .body(param.getRequestBody().toString())
                .build()
        );

        return CrawlerApiCreateResponse.of(result);
    }

    @Override
    @ValidCheck
    public void modifyCrawlerKeyword(HttpServletRequest request, CrawlerModifyRequest param) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        Crawler crawler = crawlerRepository.findById(param.getCrawlerId())
            .map(data -> {
                data.setCrawlerSiteId(param.getNewsSite().getValue());
                data.setKeyWord(param.getKeyword());
                data.setDuration(param.getDuration());
                return crawlerRepository.save(data);
            })
            .orElseThrow(() -> {
                log.error("Not found crawler data. crawler_id: {}", param.getCrawlerId());
                return new CommonException(CommonErrorMessage.NOT_FOUND);
            });

    }

    @Override
    @ValidCheck
    public CrawlerApiModifyResponse modifyCrawlerSite(HttpServletRequest request,
        CrawlerSiteModifyRequest param) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        //Note CrawlerApi의 경우 Admin 작업.
        if (!account.getAccountRole().equals(AccountRoleType.ADMIN)) {
            log.error("CrawlerAPI Setting is Only Admin Role. account_id: {}, name: {}",
                account.getUserId(), account.getUserName());
            throw new CommonException(CommonErrorMessage.FORBIDDEN);
        }

        CrawlerSite crawlerSite = crawlerApiRepository.getCrawlerBySite(param.getSiteName());
        if (Objects.isNull(crawlerSite)) {
            log.error("Not found crawler api data. param: {}", param);
            throw new CommonException(CommonErrorMessage.INVALID_PARAM);
        }
        CrawlerSite beforeData = crawlerSite;

        crawlerSite.setUrl(param.getUrl());
        crawlerSite.setBody(param.getRequestBody().toString());
        crawlerSite.setHeader(param.getRequestHeader());
        crawlerSite = crawlerApiRepository.save(crawlerSite);

        Boolean logResult = saveCrawlerApiLog(beforeData, crawlerSite);
        //TODO log Result Error처리?

        return CrawlerApiModifyResponse.of(crawlerSite);


    }

    @Override
    @ValidCheck
    public void removeCrawlerKeyword(HttpServletRequest request, Long crawlerId) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        Crawler crawler = crawlerRepository.findById(crawlerId)
            .orElseThrow(() -> {
                log.error("Not found crawler data. crawler_id: {}", crawlerId);
                return new CommonException(CommonErrorMessage.NOT_FOUND);
            });

        crawlerRepository.delete(crawler);

    }

    @Override
    @ValidCheck
    public void removeCrawlerSite(HttpServletRequest request, Long crawlerApiId) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        //Note CrawlerApi의 경우 Admin 작업.
        if (!account.getAccountRole().equals(AccountRoleType.ADMIN)) {
            log.error("CrawlerAPI Setting is Only Admin Role. account_id: {}, name: {}",
                account.getUserId(), account.getUserName());
            throw new CommonException(CommonErrorMessage.FORBIDDEN);
        }

        CrawlerSite crawlerSite = crawlerApiRepository.findById(crawlerApiId)
            .orElseThrow(() -> {
                log.error("Not found crawler api data. crawler_api_id: {}", crawlerApiId);
                return new CommonException(CommonErrorMessage.NOT_FOUND);
            });

        crawlerApiRepository.delete(crawlerSite);

        saveCrawlerApiLog(crawlerSite, null);
    }

    public Boolean saveCrawlerApiLog(CrawlerSite beforeData, CrawlerSite afterData) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            crawlerApiLogRepository.save(CrawlerSiteLog.builder()
                .beforeData(mapper.writeValueAsString(beforeData))
                .afterData(mapper.writeValueAsString(afterData))
                .crawlerApiId(beforeData.getId())
                .build());
        } catch (Exception e) {
            log.error("Failed to save repository log data. before:{},after:{}", beforeData,
                afterData);
            throw new CommonException(CommonErrorMessage.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
}
