package markigor.io.newscrawler.application.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum CommonErrorMessage implements BaseErrorMessage{
    UNKNOWN(-999, "unknown", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_PARAM(400, "Invalid Parameter", HttpStatus.BAD_REQUEST),

    BAD_REQUEST(400, "Bad request.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "Not found information.", HttpStatus.NOT_FOUND),
    INVALID_JWT(440, "Invalid JWT Token", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(500, "", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AMOUNT(600, "Invalid amount.", HttpStatus.INTERNAL_SERVER_ERROR )
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
