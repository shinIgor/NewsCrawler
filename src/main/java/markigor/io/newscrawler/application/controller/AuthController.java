package markigor.io.newscrawler.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.transfer.Request.LoginAccountRequest;
import markigor.io.newscrawler.application.model.transfer.Request.RegisterAccountRequest;
import markigor.io.newscrawler.application.model.transfer.Response.BaseResponse;
import markigor.io.newscrawler.application.model.transfer.Response.RegisterAccountResponse;
import markigor.io.newscrawler.application.model.transfer.Response.TokenResponse;
import markigor.io.newscrawler.application.service.AuthService;
import markigor.io.newscrawler.application.util.ValidCheck;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<TokenResponse> login(@NonNull @ValidCheck HttpServletRequest request,
                                             @Valid @RequestBody LoginAccountRequest param) {
        return BaseResponse.success(authService.loginAccount(request, param));
    }

    @DeleteMapping("/logout")
    public BaseResponse<?> logout(@NonNull @ValidCheck HttpServletRequest request) {
        authService.logout(request);
        return BaseResponse.success();
    }

    @PostMapping("/register")
    public BaseResponse<RegisterAccountResponse> createNewAccount(RegisterAccountRequest param) {
        return BaseResponse.success(authService.registerNewAccount(param));
    }

    @GetMapping("/register/validate")
    public BaseResponse<Boolean> checkValidate(@Valid @ModelAttribute String userId) {
        return BaseResponse.success(authService.checkDuplicateId(userId));
    }

}
