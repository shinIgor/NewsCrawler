package markigor.io.newscrawler.configuration.datasource;

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NewsCrawlerDataSource {
}
