package markigor.io.newscrawler.application.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.model.exception.CommonErrorMessage;
import markigor.io.newscrawler.application.model.exception.CommonException;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@UtilityClass
public class ValidCheckUtil {
    public void valid(Object obj) {
        Optional.ofNullable(obj)
                .orElseThrow(() -> {
                    log.error("The input parameter is the null value.");
                    return new CommonException(CommonErrorMessage.INVALID_PARAM);
                });
    }

    public void valid(Object... objList) {
        Arrays.asList(objList).forEach(ValidCheckUtil::valid);
    }
}
