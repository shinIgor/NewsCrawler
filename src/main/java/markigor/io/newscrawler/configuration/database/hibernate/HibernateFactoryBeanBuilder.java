package markigor.io.newscrawler.configuration.database.hibernate;


import jakarta.annotation.Nonnull;
import jakarta.persistence.spi.PersistenceProvider;
import lombok.extern.slf4j.Slf4j;

import markigor.io.newscrawler.application.NewsCrawler;
import markigor.io.newscrawler.configuration.database.NamingStrategyType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class HibernateFactoryBeanBuilder {
    private static final String DEFAULT_PACKAGE_NAME = NewsCrawler.class.getPackage().getName();

    private final HibernateProperties hibernateProperties;

    private DataSource dataSource;
    private String[] packagesToScans;
    private String persistenceUnitName;
    private JpaVendorAdapter jpaVendorAdapter;
    private NamingStrategyType namingStrategy = NamingStrategyType.CAMELCASE;
    private final Properties addJpaProperties = new Properties();

    private static final String DEFAULT_PERSISTENCE_UNIT_NAME_PREFIX = "PERSISTENCE-UNIT-";
    private final Class<? extends PersistenceProvider> DEFAULT_PERSISTENCE_PROVIDER_CLASS = HibernatePersistenceProvider.class;

    public HibernateFactoryBeanBuilder(@Nonnull HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    public HibernateFactoryBeanBuilder setDataSource(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public HibernateFactoryBeanBuilder setPackagesToScans(String... packagesToScans) {
        this.packagesToScans = packagesToScans;
        return this;
    }

    public HibernateFactoryBeanBuilder setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
        return this;
    }

    public HibernateFactoryBeanBuilder setJpaVendorAdapter(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
        return this;
    }

    public HibernateFactoryBeanBuilder setNamingStrategy(NamingStrategyType namingStrategy) {
        this.namingStrategy = namingStrategy;
        return this;
    }

    public HibernateFactoryBeanBuilder addJpaProperty(String key, String value) {
        addJpaProperties.setProperty(key, value);
        return this;
    }

    public LocalContainerEntityManagerFactoryBean build() {
        if (null == dataSource) {
            throw new IllegalStateException("DataSource 가 없습니다. 반드시 지정해야 합니다.");
        }

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        addDataSource(entityManagerFactoryBean);
        addPackagesToScan(entityManagerFactoryBean);
        addPersistenceUnitName(entityManagerFactoryBean);
        addPersistenceProviderClass(entityManagerFactoryBean);
        addJpaVendorAdapter(entityManagerFactoryBean);
        addJpaProperties(entityManagerFactoryBean);

        return entityManagerFactoryBean;
    }

    private void addDataSource(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        entityManagerFactoryBean.setDataSource(dataSource);
    }

    private void addPackagesToScan(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        if (ArrayUtils.isNotEmpty(packagesToScans)) {
            entityManagerFactoryBean.setPackagesToScan(packagesToScans);
            return;
        }

        if (ArrayUtils.isNotEmpty(hibernateProperties.getPackagesToScans())) {
            entityManagerFactoryBean.setPackagesToScan(hibernateProperties.getPackagesToScans());
            return;
        }

        entityManagerFactoryBean.setPackagesToScan(DEFAULT_PACKAGE_NAME);
    }

    private void addPersistenceUnitName(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        if (Strings.isNotEmpty(persistenceUnitName)) {
            entityManagerFactoryBean.setPersistenceUnitName(persistenceUnitName);
            return;
        }

        entityManagerFactoryBean.setPersistenceUnitName(getDefaultPersistenceUnitName());
    }

    private String getDefaultPersistenceUnitName() {
        return (DEFAULT_PERSISTENCE_UNIT_NAME_PREFIX + hibernateProperties.getName()).toUpperCase();
    }

    private void addPersistenceProviderClass(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        entityManagerFactoryBean.setPersistenceProviderClass(DEFAULT_PERSISTENCE_PROVIDER_CLASS);
    }

    private void addJpaVendorAdapter(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        if (null != jpaVendorAdapter) {
            entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
            return;
        }

        entityManagerFactoryBean.setJpaVendorAdapter(getDefaultJpaVendorAdapter());
    }

    protected JpaVendorAdapter getDefaultJpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(hibernateProperties.isShowSql());
        jpaVendorAdapter.setGenerateDdl(hibernateProperties.isGenerateDdl());

        return jpaVendorAdapter;
    }

    private void addJpaProperties(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        entityManagerFactoryBean.setJpaProperties(getDefaultJpaProperties());
        entityManagerFactoryBean.setJpaProperties(addJpaProperties);
    }

    /**
     * {@link org.hibernate.cfg.AvailableSettings}
     *
     * @return Properties
     */
    private Properties getDefaultJpaProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.use_sql_comments", hibernateProperties.getUseSqlComments());
        properties.setProperty("hibernate.format_sql", hibernateProperties.getFormatSql());
        properties.setProperty("hibernate.enable_lazy_load_no_trans", hibernateProperties.getEnableLazyLoadNoTrans());

        // namingStrategy 설정이 기본이고 Properties 값이 다를 경우 Properties 값이 우선된다.
        if (NamingStrategyType.CAMELCASE.equals(namingStrategy)
                && !Objects.equals(namingStrategy, hibernateProperties.getNamingStrategy())) {
            namingStrategy = hibernateProperties.getNamingStrategy();
        }

        // 핑
        if (!NamingStrategyType.DIRECT_INPUT.equals(namingStrategy)) {
            properties.setProperty(namingStrategy.getKey(), namingStrategy.getStrategy());
        }

        return properties;
    }
}
