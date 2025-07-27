package markigor.io.newscrawler.application.util;

import java.util.Optional;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;

@Slf4j
@UtilityClass
public class PreConditionalUtil {

    public static <T> void valid(T obj) {
        Optional.ofNullable(obj)
            .orElseThrow(() -> {
                log.error("param is null value.");
                return new CommonException(CommonErrorMessage.INVALID_PARAM);
            });
    }
}
