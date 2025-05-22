package markigor.io.newscrawler.application.model.transfer.Dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.transfer.Dto.Token.AccessToken;
import markigor.io.newscrawler.application.model.transfer.Dto.Token.RefreshToken;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionTokenDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6274256903978987816L;

    AccessToken accessToken;
    RefreshToken refreshToken;
    Map<String, Object> additionalInformation;


    public SessionTokenDto(AccessToken accessToken, RefreshToken refreshToken, Map<String, Object> additionalInformation) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.additionalInformation = additionalInformation;
    }
}
