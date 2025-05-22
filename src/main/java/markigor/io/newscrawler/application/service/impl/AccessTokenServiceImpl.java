package markigor.io.newscrawler.application.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.define.RedisCacheKeyDefine;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.transfer.Dto.SessionTokenDto;
import markigor.io.newscrawler.application.model.transfer.Dto.Token.AccessToken;
import markigor.io.newscrawler.application.model.transfer.Dto.Token.RefreshToken;
import markigor.io.newscrawler.application.model.transfer.Response.TokenResponse;
import markigor.io.newscrawler.application.service.AccessTokenService;
import markigor.io.newscrawler.application.service.RedisCacheService;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {
    private final RedisCacheService redisCacheService;


    @Override
    @ValidCheck
    public TokenResponse createAccessToken(HttpServletRequest request, Account account) {
        AccessToken accessToken = new AccessToken(account.getId());
        RefreshToken refreshToken = new RefreshToken(account.getId());

        TokenResponse result = new TokenResponse(accessToken, refreshToken, account);
        SessionTokenDto sessionTokenDto = new SessionTokenDto(
                result.getAccessToken(),
                result.getRefreshToken(),
                result.getAdditionalInformation());

        List<SessionTokenDto> storedTokenDtoList = redisCacheService.getAllList(RedisCacheKeyDefine.getUsnToTokenKey(account.getId()), SessionTokenDto.class);
        for (SessionTokenDto dto : storedTokenDtoList) {
            redisCacheService.removeValue(RedisCacheKeyDefine.getAccessTokenKey(dto.getAccessToken().getValue()));
            redisCacheService.removeValue(RedisCacheKeyDefine.getRefreshTokenKey(dto.getRefreshToken().getValue()));
        }
        redisCacheService.removeValue(RedisCacheKeyDefine.getUsnToTokenKey(account.getId()));
        redisCacheService.setValue(RedisCacheKeyDefine.getAccessTokenKey(accessToken.getValue()), sessionTokenDto, 1, TimeUnit.HOURS);
        redisCacheService.setValue(RedisCacheKeyDefine.getRefreshTokenKey(refreshToken.getValue()), sessionTokenDto, 2, TimeUnit.HOURS);
        redisCacheService.leftPushList(RedisCacheKeyDefine.getUsnToTokenKey(account.getId()), sessionTokenDto, 3, TimeUnit.HOURS);

        return new TokenResponse(accessToken, refreshToken, account);
    }

    @Override
    @ValidCheck
    public TokenResponse refreshToken(HttpServletRequest request, String refreshTokenValue) {
        return null;
    }


}