package markigor.io.newscrawler.application.model.type;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
public enum NewsCrawlerApiSiteType {
    UNKNOWN("unknown", 0L),
    GOOGLE("user", 1L),
    NAVER("naver", 2L),
    DAUM("daum", 3L),
    KAKAO("kakao", 3L),
    ;


    private final String site;
    private final Long value;


    NewsCrawlerApiSiteType(String site, Long value) {
        this.site = site;
        this.value = value;
    }

    private static NewsCrawlerApiSiteType find(Predicate<NewsCrawlerApiSiteType> predicate) {
        return Arrays.stream(values())
                .filter(predicate)
                .findAny()
                .orElse(null);
    }

    public static NewsCrawlerApiSiteType fromCode(Long code) {
        for (NewsCrawlerApiSiteType site : NewsCrawlerApiSiteType.values()) {
            if (site.getValue().equals(code)) {
                return site;
            }
        }
        return UNKNOWN;
    }

    public static NewsCrawlerApiSiteType from(String role) {
        return find(entity -> Objects.equals(entity.site, role));
    }
}
