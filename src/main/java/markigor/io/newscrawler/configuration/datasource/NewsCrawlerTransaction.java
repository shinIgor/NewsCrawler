package markigor.io.newscrawler.configuration.datasource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = "transactionManagerNewsCrawler",  propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
public @interface NewsCrawlerTransaction {
}
