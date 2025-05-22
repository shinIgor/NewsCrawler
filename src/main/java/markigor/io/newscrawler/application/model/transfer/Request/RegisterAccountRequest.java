package markigor.io.newscrawler.application.model.transfer.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterAccountRequest {
    @NotNull
    String Id;
    @NotNull
    String password;
    @NotNull
    String name;
}
