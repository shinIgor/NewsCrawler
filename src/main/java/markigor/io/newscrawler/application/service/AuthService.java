package markigor.io.newscrawler.application.service;

import jakarta.servlet.http.HttpServletRequest;
import markigor.io.newscrawler.application.model.transfer.Request.LoginAccountRequest;
import markigor.io.newscrawler.application.model.transfer.Request.RegisterAccountRequest;
import markigor.io.newscrawler.application.model.transfer.Response.RegisterAccountResponse;
import markigor.io.newscrawler.application.model.transfer.Response.TokenResponse;

public interface AuthService {
    RegisterAccountResponse registerNewAccount(RegisterAccountRequest param);

    TokenResponse loginAccount(HttpServletRequest request, LoginAccountRequest param);

    void logout(HttpServletRequest request);

    Boolean checkDuplicateId(String Id);
}
