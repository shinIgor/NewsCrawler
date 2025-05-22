package markigor.io.newscrawler.configuration.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import markigor.io.newscrawler.application.model.transfer.Request.JwtRequest;

import java.io.IOException;

@Slf4j
// NOTE: url 패턴 맞춰야함
@WebFilter(urlPatterns = {"/news/v1/auth/*"})
public class JwtAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request == null) throw new CommonException(CommonErrorMessage.INTERNAL_SERVER_ERROR);
        final HttpServletRequest req = (HttpServletRequest) request;
        final String tokenValue = extractHeaderUnsignedToken(req);

        try {
            final Claims claims = Jwts.parser().parseClaimsJwt(tokenValue).getBody();
            JwtRequest jwtRequest = new JwtRequest(claims);
            req.setAttribute(JwtRequest.class.getName(), jwtRequest);
        } catch (Exception e) {
            log.error("fail check JWT : {}", tokenValue);
            throw new CommonException(CommonErrorMessage.INVALID_JWT);
        }
        chain.doFilter(request, response);
    }


    protected String extractHeaderUnsignedToken(HttpServletRequest request) {
        String headers = request.getHeader("Authorization");

        if (headers == null || ! headers.startsWith("Bearer ")) {
            throw new CommonException(CommonErrorMessage.INVALID_JWT);
        }
        String[] split = headers.substring("Bearer ".length()).split("\\.");
        return split[0] + "." + split[1] + ".";
    }
}
