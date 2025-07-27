package markigor.io.newscrawler.application.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum CommonErrorMessage implements BaseErrorMessage {
    UNKNOWN(-999, "unknown", HttpStatus.INTERNAL_SERVER_ERROR),

    FAIL_PARSE(102, "Fail to Parse", HttpStatus.BAD_REQUEST),

    INVALID_PARAM(400, "Invalid Parameter", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400, "Invalid Password", HttpStatus.BAD_REQUEST),
    DUPLICATE_REQUEST(400, "Duplicate Request", HttpStatus.BAD_REQUEST),


    BAD_REQUEST(400, "Bad request.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN),
    UNKNOWN_USN(403, "Unknow user id", HttpStatus.FORBIDDEN),

    NOT_FOUND(404, "Not found information.", HttpStatus.NOT_FOUND),
    INVALID_JWT(440, "Invalid JWT Token", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(500, "", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AMOUNT(600, "Invalid amount.", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_ACCESS_TOKEN(1001, "Invalid jwt token.", HttpStatus.UNAUTHORIZED),
    ;

    final Integer code;
    final String message;
    final HttpStatus status;

    CommonErrorMessage(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    CommonErrorMessage find(Predicate<CommonErrorMessage> predicate) {
        return Arrays.stream(values())
                .filter(predicate)
                .findAny()
                .orElse(UNKNOWN);
    }
}
