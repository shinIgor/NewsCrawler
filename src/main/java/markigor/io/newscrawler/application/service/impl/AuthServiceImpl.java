package markigor.io.newscrawler.application.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.define.RedisCacheKeyDefine;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.application.model.repository.AccountRepository;
import markigor.io.newscrawler.application.model.transfer.Dto.SessionTokenDto;
import markigor.io.newscrawler.application.model.transfer.Request.LoginAccountRequest;
import markigor.io.newscrawler.application.model.transfer.Request.RegisterAccountRequest;
import markigor.io.newscrawler.application.model.transfer.Response.RegisterAccountResponse;
import markigor.io.newscrawler.application.model.transfer.Response.TokenResponse;
import markigor.io.newscrawler.application.model.type.AccountRoleType;
import markigor.io.newscrawler.application.service.AccessTokenService;
import markigor.io.newscrawler.application.service.AuthService;
import markigor.io.newscrawler.application.service.RedisCacheService;
import markigor.io.newscrawler.application.util.BCryptUtil;
import markigor.io.newscrawler.application.util.HttpHeaderUtil;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final AccessTokenService accessTokenService;
    private final RedisCacheService redisCacheService;


    @Override
    @ValidCheck
    public RegisterAccountResponse registerNewAccount(RegisterAccountRequest param) {
        Boolean isExist = isExistId(param.getId());
        if (isExist) {
            log.warn("Register duplicate user id. userId: {}", param.getId());
            throw new CommonException(CommonErrorMessage.INVALID_PARAM);
        }

        //Note Password 암호화
        String hashPassword = BCryptUtil.encodePassword(param.getPassword());

        Account result = accountRepository.save(
                Account.builder()
                        .userId(param.getId())
                        .userPassword(hashPassword)
                        .userName(param.getName())
                        .accountRole(AccountRoleType.USER)
                        .lastProcessedAt(LocalDateTime.now())
                        .build()
        );
        return RegisterAccountResponse.of(result);
    }

    @Override
    @ValidCheck
    public TokenResponse loginAccount(@NonNull @ValidCheck HttpServletRequest request, @ValidCheck @NotEmpty LoginAccountRequest param) {
        Account account = accountRepository.getAccount(param.getUserId());

        //Note User null check
        if (Objects.isNull(account)) {
            log.error("Unknown user. user_id: {}", param.getUserId());
            throw new CommonException(CommonErrorMessage.INVALID_PARAM);
        }

        //Note 암호화 된
        Boolean isMatch = BCryptUtil.isMatch(account.getUserPassword(), param.getUserPassword());
        if (!isMatch) {
            log.error("Invalid user password. user_id: {}", param.getUserId());
            throw new CommonException(CommonErrorMessage.INVALID_PASSWORD);
        }

        //TODO 최신 뉴스 데이터 캐싱
        TokenResponse tokenResponse = accessTokenService.createAccessToken(request, account);

        return tokenResponse;
    }

    @Override
    @ValidCheck
    public void logout(HttpServletRequest request) {
        String accessToken = HttpHeaderUtil.getExtractBearerToken(request);
        SessionTokenDto sessionTokenDto = redisCacheService.getValue(RedisCacheKeyDefine.getAccessTokenKey(accessToken), SessionTokenDto.class);

        if (sessionTokenDto == null) {
            log.error("Not found access token");
            throw new CommonException(CommonErrorMessage.INVALID_ACCESS_TOKEN);
        }
        redisCacheService.removeValue(RedisCacheKeyDefine.getAccessTokenKey(sessionTokenDto.getAccessToken().getValue()));
        redisCacheService.removeValue(RedisCacheKeyDefine.getRefreshTokenKey(sessionTokenDto.getRefreshToken().getValue()));
    }

    @Override
    @ValidCheck
    public Boolean checkDuplicateId(@ValidCheck @NotEmpty String Id) {
        Boolean isExist = isExistId(Id);
        if (isExist) {
            log.warn("Register duplicate user id. userId: {}", Id);
            throw new CommonException(CommonErrorMessage.INVALID_PARAM);
        }
        return true;
    }

    @ValidCheck
    public Boolean isExistId(@ValidCheck @NotEmpty String Id) {
        Boolean isExist = accountRepository.existsAccountByUserId(Id);
        return isExist;
    }
}
