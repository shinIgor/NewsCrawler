package markigor.io.newscrawler.application.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;

@Slf4j
@UtilityClass
public class HttpHeaderUtil {

    private static final String UNKNOWN = "Unknown";
    private static final String OTHER = "Other";

    public String getAuthorizationToken(HttpServletRequest request) {
        String headers = request.getHeader("Authorization");

        if (headers == null || !headers.startsWith("Bearer ")) {
            return null;
        }
        String[] split = headers.substring("Bearer ".length()).split("\\.");
        return split[0] + "." + split[1] + ".";
    }

    public String getAuthorizationAccessToken(HttpServletRequest request) {
        String headers = request.getHeader("Authorization");

        if (headers == null || !headers.startsWith("Bearer ")) {
            return null;
        }
        return headers.substring("Bearer ".length());
    }

    public String getExtractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (null == header || !header.startsWith("Bearer ")) {
            throw new CommonException(CommonErrorMessage.UNAUTHORIZED);
        }

        return header.substring("Bearer ".length());
    }

    public String getIp(HttpServletRequest request) {
        final String[] forwardedFor = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
            .map(x -> x.split(",")).orElseGet(() -> new String[]{});
        if (forwardedFor.length > 0) {
            return forwardedFor[0];
        }
        return request.getRemoteAddr();
    }

}