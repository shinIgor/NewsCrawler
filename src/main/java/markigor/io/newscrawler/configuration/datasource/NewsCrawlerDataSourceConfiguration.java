package markigor.io.newscrawler.configuration.datasource;

import lombok.extern.slf4j.Slf4j;
import markigor.io.newscrawler.application.NewsCrawler;
import markigor.io.newscrawler.configuration.database.BaseDataSourceBuilder;
import markigor.io.newscrawler.configuration.database.hibernate.HibernateFactoryBeanBuilder;
import markigor.io.newscrawler.configuration.database.hibernate.HibernateProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableJpaRepositories(
        basePackageClasses = { NewsCrawler.class },
        entityManagerFactoryRef = "entityManagerFactoryNewsCrawler",
        transactionManagerRef = "transactionManagerNewsCrawler",
        includeFilters = @ComponentScan.Filter(classes = NewsCrawlerDataSource.class)
)
public class NewsCrawlerDataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.news")
    public HibernateProperties datasourcePropertiesNewsCrawler() {
        return new HibernateProperties();
    }

    @Bean
    public DataSource dataSourceNewsCrawler() {
        return new BaseDataSourceBuilder(datasourcePropertiesNewsCrawler()).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryNewsCrawler() {
        return new HibernateFactoryBeanBuilder(datasourcePropertiesNewsCrawler())
                .setDataSource(dataSourceNewsCrawler())
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerNewsCrawler() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryNewsCrawler().getObject());

        return jpaTransactionManager;
    }
}
