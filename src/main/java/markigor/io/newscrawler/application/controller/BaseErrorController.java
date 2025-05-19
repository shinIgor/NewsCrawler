package markigor.io.newscrawler.application.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class BaseErrorController implements ErrorController {
    private final static String ERROR_NOT_FOUND = "exception/not_found";
    private final static String ERROR_BAD_REQUEST = "exception/bad_request";
    private final static String ERROR_FORBIDDEN = "exception/forbidden";
    private final static String ERROR_INTERNAL_SERVER = "exception/internal_server_error";
    private final static String ERROR_UNAUTHORIZED = "exception/unauthorized";
    private final static String ERROR_UNKNOWN = "exception/unknown";

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request,
                                    @RequestParam(name = "code", required = false) Integer code) {
        HttpStatus status = Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .map(e -> {
                    return HttpStatus.valueOf(Integer.parseInt(e.toString()));
                })
                .orElseGet(() -> {
                    return HttpStatus.valueOf(code);
                });

        return getErrorPage(status);
    }

    private ModelAndView getErrorPage(HttpStatus status) {
        ModelAndView mnv = new ModelAndView();
        mnv.addObject("title", status.getReasonPhrase());
        mnv.addObject("status", status.value());


        switch (status) {
            case NOT_FOUND:
                mnv.setViewName(ERROR_NOT_FOUND);
                return mnv;
            case BAD_REQUEST:
                mnv.setViewName(ERROR_BAD_REQUEST);
                return mnv;
            case FORBIDDEN:
                mnv.setViewName(ERROR_FORBIDDEN);
                return mnv;
            case INTERNAL_SERVER_ERROR:
                mnv.setViewName(ERROR_INTERNAL_SERVER);
                return mnv;
            case UNAUTHORIZED:
                mnv.setViewName(ERROR_UNAUTHORIZED);
                return mnv;
            default:
                return getDefaultPage();
        }
    }

    private ModelAndView getDefaultPage() {
        ModelAndView mnv = new ModelAndView();
        mnv.addObject("title", "Oops");
        mnv.addObject("status", "Unknown");
        mnv.setViewName(ERROR_UNKNOWN);
        return mnv;
    }
}