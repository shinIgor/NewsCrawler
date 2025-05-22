package markigor.io.newscrawler.application.model.define;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RedisCacheKeyDefine {
    private final String REDIS_KEY_DELIMITER = ":";

    public String getMemberKey(Long usn) {
        return String.join(REDIS_KEY_DELIMITER, DefineDefaultValue.SERVICE_NAME,
                "usn", usn.toString());
    }

    public String getAccessTokenKey(String accessToken) {
        return String.join(REDIS_KEY_DELIMITER, DefineDefaultValue.SERVICE_NAME,
                "token", "access", accessToken);
    }
    public String getRefreshTokenKey(String refreshToken) {
        return String.join(REDIS_KEY_DELIMITER, DefineDefaultValue.SERVICE_NAME,
                "token", "refresh", refreshToken);
    }

    public String getUsnToTokenKey(Long usn) {
        return String.join(REDIS_KEY_DELIMITER, DefineDefaultValue.SERVICE_NAME,
                "token", "usn_to_token", usn.toString());
    }
}
