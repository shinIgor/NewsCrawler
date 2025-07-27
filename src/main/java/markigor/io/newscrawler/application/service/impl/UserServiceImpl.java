package markigor.io.newscrawler.application.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.entity.Crawler;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.application.model.repository.CrawlerRepository;
import markigor.io.newscrawler.application.model.repository.UserServiceRepository;
import markigor.io.newscrawler.application.model.transfer.Request.UserServiceCreateRequest;
import markigor.io.newscrawler.application.model.transfer.Response.UserServiceCreateResponse;
import markigor.io.newscrawler.application.service.UserService;
import markigor.io.newscrawler.application.util.SessionUtil;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CrawlerRepository crawlerRepository;
    private final UserServiceRepository userServiceRepository;

    @Override
    @ValidCheck
    @Transactional
    public UserServiceCreateResponse createCrawlerKeyword(HttpServletRequest request,
        UserServiceCreateRequest param) {
        // Note Session 체크
        Account account = SessionUtil.getAccountBySession(request);

        //Note
        // 1. KeyWord 존재 유/무 조회. if 있다면: userService 테이블 추가 / if 없다면: crawlerData 추가 후 userService 추가.
        Crawler crawler = crawlerRepository.getCrawlerByKeyword(param.getKeyword(),
            param.getDuration());
        if (Objects.isNull(crawler)) {
            log.info("Request new keyword crawler service. keyword: {}, duration: {}",
                param.getKeyword(), param.getDuration());
            crawler = crawlerRepository.save(Crawler.builder()
                .crawlerSiteId(param.getNewsSite().getValue())
                .keyWord(param.getKeyword())
                .duration(param.getDuration())
                .isScrap(true)
                .build());
        }

        //Note
        // UserService에 해당 유저의 Crawler 서비스 추가.
        markigor.io.newscrawler.application.model.entity.UserService userService = userServiceRepository.getUserServiceByCrawler(
            crawler.getId());
        if (!Objects.isNull(userService)) {
            log.error("Duplicate create request. param: {}, duplicate_data: {}", param,
                userService);
            throw new CommonException(CommonErrorMessage.DUPLICATE_REQUEST);
        }
        userService = userServiceRepository.save(
            markigor.io.newscrawler.application.model.entity.UserService.builder()
                .accountId(account.getId())
                .crawlerId(crawler.getId())
                .build());

        return UserServiceCreateResponse.of(userService, crawler);
    }

}
