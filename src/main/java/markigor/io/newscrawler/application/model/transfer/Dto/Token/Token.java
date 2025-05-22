package markigor.io.newscrawler.application.model.transfer.Dto.Token;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Token {
    protected String value;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    protected LocalDateTime expiredAt;
}
