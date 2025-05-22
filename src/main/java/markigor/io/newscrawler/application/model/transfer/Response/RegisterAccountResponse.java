package markigor.io.newscrawler.application.model.transfer.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.Account;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterAccountResponse {
    String userId;
    String userName;
    LocalDateTime createdAt;

    public static RegisterAccountResponse of(Account param) {
        RegisterAccountResponse result = new RegisterAccountResponse();
        if (Objects.isNull(param)) return result;
        result.userId = param.getUserId();
        result.userName = param.getUserPassword();
        result.createdAt = param.getCreatedAt();
        return result;
    }
}
