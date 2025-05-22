package markigor.io.newscrawler.application.client;

import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class NaverAPIClient {
    private final WebClient webClient;
    private final Duration DEFAULT_TIMEOUT_SECOND = Duration.ofSeconds(3L);

    public NaverAPIClient(WebClient webClient, @Value("${app.host.naver}") String apiHost) {
        this.webClient = webClient.mutate().baseUrl(apiHost).build();
    }

    private <T> Mono<T> makeErrorResume(Throwable throwable) {
        String message = MessageFormat.format("Failed to webclient process. error-msg: {0}", throwable.getMessage());
        log.error(message);
        return Mono.error(new RuntimeException(message));
    }

    private void validInputParam(Object obj) {
        Optional.ofNullable(obj)
                .orElseThrow(() -> {
                    log.error("The input parameter is the null value.");
                    return new CommonException(CommonErrorMessage.INVALID_PARAM);
                });
    }

    private void validInputParam(Object... objList) {
        Arrays.asList(objList).forEach(this::validInputParam);
    }
}
