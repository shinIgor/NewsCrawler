package markigor.io.newscrawler.application.model.transfer.Dto.Token;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessToken extends Token {

    private static final int ACCESS_TOKEN_VALIDITY_HOUR = 4;

    public AccessToken(Long userId) {
        this.value = UUID.randomUUID() + ":" + userId;
        this.expiredAt = LocalDateTime.now().plusHours(ACCESS_TOKEN_VALIDITY_HOUR);
    }

    public AccessToken(String value, int time) {
        this.value = value;
        this.expiredAt = LocalDateTime.now().plusHours(time);
    }
}
