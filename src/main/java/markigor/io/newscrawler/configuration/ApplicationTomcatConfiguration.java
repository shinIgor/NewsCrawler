package markigor.io.newscrawler.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class ApplicationTomcatConfiguration {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addContextCustomizers(new ContextSecurityCustomizer());
    }

    private static class ContextSecurityCustomizer implements TomcatContextCustomizer {
        @Override
        public void customize(Context context) {
            SecurityConstraint constraint = new SecurityConstraint();
            SecurityCollection securityCollection = new SecurityCollection();
            securityCollection.setName("restricted_methods");
            securityCollection.addPattern("/*");
            securityCollection.addMethod(HttpMethod.HEAD.toString());
            securityCollection.addMethod(HttpMethod.PATCH.toString());
            securityCollection.addMethod(HttpMethod.TRACE.toString());

            constraint.addCollection(securityCollection);
            constraint.setAuthConstraint(true);

            context.addConstraint(constraint);
        }
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizerError() {
        return (TomcatServletWebServerFactory factory) -> {
            log.info("—> CustomTomcatErrorConfiguration start.");
            factory.addContextCustomizers(context -> {
                Container parent = context.getParent();
                if (parent instanceof StandardHost) {
                    ((StandardHost) parent).addValve(new CustomTomcatErrorValve());
                    ((StandardHost) parent).setErrorReportValveClass(CustomTomcatErrorValve.class.getName());
                }
            });
        };
    }

    private static class CustomTomcatErrorValve extends ErrorReportValve {
        private final ObjectMapper objectMapper = new ObjectMapper();
        @Override
        protected void report(Request request, Response response, Throwable throwable) {
            if (!response.setErrorReported()) {
                return;
            }

            log.error("[{}] Fatal error before getting to Spring.", response.getStatus());
            response.setSuspended(true);

            try {
                String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

                if (null != contentType && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                    response.finishResponse();
                    return;
                }

                response.setStatus(HttpStatus.FOUND.value());
                response.setHeader("Location", "/error?code=400");
                response.finishResponse();
            } catch (IOException ex) {
                log.error("Exception. msg: {}", ex.getMessage());
            }
        }
    }

}