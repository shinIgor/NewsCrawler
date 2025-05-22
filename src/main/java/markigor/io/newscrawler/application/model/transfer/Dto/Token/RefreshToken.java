package markigor.io.newscrawler.application.model.transfer.Dto.Token;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken extends Token {

    private static final int REFRESH_TOKEN_VALIDITY_HOUR = 4;

    public RefreshToken(Long userId) {
        this.value = UUID.randomUUID() + ":" + userId;
        this.expiredAt = LocalDateTime.now().plusHours(REFRESH_TOKEN_VALIDITY_HOUR);
    }

    public RefreshToken(String value, int time) {
        this.value = value;
        this.expiredAt = LocalDateTime.now().plusHours(time);
    }
}
