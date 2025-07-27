package markigor.io.newscrawler.application.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.define.RedisCacheKeyDefine;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.application.model.repository.AccountRepository;
import markigor.io.newscrawler.application.model.transfer.Dto.SessionTokenDto;
import markigor.io.newscrawler.application.model.type.AdditionalInformation;
import markigor.io.newscrawler.application.service.RedisCacheService;

@Slf4j
@UtilityClass
public class SessionUtil {

    private RedisCacheService redisCacheService;
    private AccountRepository accountRepository;


    public Account getAccountBySession(HttpServletRequest request) {
        //Note API User Session 체크
        String accessToken = HttpHeaderUtil.getExtractBearerToken(request);
        SessionTokenDto sessionTokenDto = redisCacheService.getValue(
            RedisCacheKeyDefine.getAccessTokenKey(accessToken), SessionTokenDto.class);
        if (Objects.isNull(sessionTokenDto)) {
            log.error("Not found session. accessToken: {}", accessToken);
            throw new CommonException(CommonErrorMessage.INVALID_ACCESS_TOKEN);
        }

        //Note 유저 Account_ID 기준으로 해당 유저 데이터 정보 조회. (Session을 받았지만 DB에 데이터 없는걸 방지하기 위함)
        Map<String, Object> additional = sessionTokenDto.getAdditionalInformation();
        Long accountId = (Long) additional.getOrDefault(AdditionalInformation.USN.getName(), null);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> {
                log.error("Not found account Id. USN: {}", accountId);
                return new CommonException(CommonErrorMessage.UNKNOWN_USN);
            });
        return account;
    }


}
