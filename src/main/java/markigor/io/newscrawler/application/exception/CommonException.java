package markigor.io.newscrawler.application.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonException extends RuntimeException {
    private final BaseErrorMessage type;

    public String getMessage() {
        return Optional.ofNullable(type)
                .map(BaseErrorMessage::getMessage)
                .orElseGet(CommonErrorMessage.UNKNOWN::getMessage);
    }

    public Integer getCode() {
        return Optional.ofNullable(type)
                .map(BaseErrorMessage::getCode)
                .orElseGet(CommonErrorMessage.UNKNOWN::getCode);
    }

    public HttpStatus getStatus() {
        return Optional.ofNullable(type)
                .map(BaseErrorMessage::getStatus)
                .orElseGet(CommonErrorMessage.UNKNOWN::getStatus);
    }
}
