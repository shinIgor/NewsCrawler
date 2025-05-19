package markigor.io.newscrawler.application.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseError {
    Integer code;
    String message;

    public BaseError(BaseErrorMessage type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }
}
