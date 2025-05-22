package markigor.io.newscrawler.application.model.transfer.Response;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import markigor.io.newscrawler.application.model.entity.Account;
import markigor.io.newscrawler.application.model.transfer.Dto.Token.AccessToken;
import markigor.io.newscrawler.application.model.transfer.Dto.Token.RefreshToken;
import markigor.io.newscrawler.application.model.type.AdditionalInformation;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse {
    AccessToken accessToken;
    RefreshToken refreshToken;
    Map<String, Object> additionalInformation;

    public TokenResponse(AccessToken accessToken, RefreshToken refreshToken, Account account) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

        this.additionalInformation = new HashMap<>();
        if(account != null) {
            this.additionalInformation.put(AdditionalInformation.USN.getName(), account.getId());
            this.additionalInformation.put(AdditionalInformation.USER_ID.getName(), account.getUserId());
            this.additionalInformation.put(AdditionalInformation.NAME.getName(), account.getUserName());
            this.additionalInformation.put(AdditionalInformation.IS_CONNECT.getName(), true);
        } else {
            this.additionalInformation.put(AdditionalInformation.IS_CONNECT.getName(), false);
        }
    }
}
