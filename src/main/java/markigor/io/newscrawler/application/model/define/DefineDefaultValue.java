package markigor.io.newscrawler.application.model.define;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class DefineDefaultValue {
    public static final String SERVICE_NAME = "news-crawler";

    public static final Duration WEBCLIENT_RETRY_DELAY_SECOND = Duration.ofSeconds(3);
}
