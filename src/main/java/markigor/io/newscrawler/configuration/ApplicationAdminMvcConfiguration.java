package markigor.io.newscrawler.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class ApplicationAdminMvcConfiguration implements WebMvcConfigurer {
    private final Environment env;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(webContentInterceptor());

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public WebContentInterceptor webContentInterceptor() {
        CacheControl cacheControl = CacheControl.noStore()
                .sMaxAge(Duration.ZERO)
                .mustRevalidate();

        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.addCacheMapping(cacheControl, "/**");

        return webContentInterceptor;
    }

    private List<String> interceptorExcludePath() {
        List<String> excludePath = new ArrayList<>();

        return excludePath;
    }
}