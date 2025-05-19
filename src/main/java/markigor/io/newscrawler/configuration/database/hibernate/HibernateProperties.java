package markigor.io.newscrawler.configuration.database.hibernate;


import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import markigor.io.newscrawler.configuration.database.BaseDataSourceProperties;
import markigor.io.newscrawler.configuration.database.NamingStrategyType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HibernateProperties extends BaseDataSourceProperties {
    private boolean showSql = true;
    private boolean generateDdl = false;
    private String useSqlComments = "true";
    private String formatSql = "true";
    private String enableLazyLoadNoTrans = "false";
    private NamingStrategyType namingStrategy = NamingStrategyType.CAMELCASE;
    private String[] packagesToScans = {
            "markigor.io"
    };



}
