package markigor.io.newscrawler.configuration.database;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum NamingStrategyType {
    @Deprecated
    PHYSICAL("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"),
    CAMELCASE("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"),
    IMPLICIT("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"),
    DIRECT_INPUT(null, null);

    final  String key;

    final String strategy;

    NamingStrategyType(String key, String strategy) {
        this.key = key;
        this.strategy = strategy;
    }
}
