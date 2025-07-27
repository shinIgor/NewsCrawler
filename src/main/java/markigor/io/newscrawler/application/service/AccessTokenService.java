package markigor.io.newscrawler.application.service;

import jakarta.servlet.http.HttpServletRequest;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.transfer.Response.TokenResponse;


public interface AccessTokenService {

    TokenResponse createAccessToken(HttpServletRequest request, Account account);

    TokenResponse refreshToken(HttpServletRequest request, String refreshTokenValue);
}
