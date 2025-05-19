package markigor.io.newscrawler.application.controller;

import lombok.RequiredArgsConstructor;
import markigor.io.newscrawler.application.model.transfer.Response.BaseResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news/v1/auth")
public class AuthController {

    @PostMapping("/longin")
    public BaseResponse<?> login() {
        return BaseResponse.success();
    }

    @DeleteMapping("/logout")
    public BaseResponse<?> logout(){
        return BaseResponse.success();
    }

}
