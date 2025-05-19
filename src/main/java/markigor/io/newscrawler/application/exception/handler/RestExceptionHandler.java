package markigor.io.newscrawler.application.exception.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.exception.BaseError;
import markigor.io.newscrawler.application.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.exception.CommonException;
import markigor.io.newscrawler.application.model.transfer.Response.BaseResponse;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleCommonException(CommonException ex) {
        log.error("Common Exception -> exception-msg: {} ", ex.getMessage());
        return new ResponseEntity<>(BaseResponse.fail(new BaseError(ex.getType())),
                ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Exception -> {}", ex.getMessage());
        return new ResponseEntity<>(BaseResponse.fail(new BaseError(CommonErrorMessage.INTERNAL_SERVER_ERROR)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<?> handleBindException(BindException ex) {
        log.error("Bind Exception -> {} ", ex.getMessage());
        return new ResponseEntity<>(BaseResponse.fail(new BaseError(CommonErrorMessage.BAD_REQUEST)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException ex) {
        log.error("InvalidParameterException: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(BaseResponse.fail(new BaseError(CommonErrorMessage.INVALID_PARAM)),
                HttpStatus.BAD_REQUEST);
    }
}
