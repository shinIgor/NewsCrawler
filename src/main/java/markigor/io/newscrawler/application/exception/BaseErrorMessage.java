package markigor.io.newscrawler.application.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorMessage {
    Integer getCode();
    String getMessage();
    HttpStatus getStatus();
}
