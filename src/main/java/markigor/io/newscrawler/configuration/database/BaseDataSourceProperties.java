package markigor.io.newscrawler.configuration.database;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseDataSourceProperties {
    String name;
    String driverClassName;
    String jdbcUrl;
    String username;
    String password;
    int minimumIdle = 30;
    int maximumPoolSize = 30;
    int maxLifetime = 1800000;				// 30 minutes
    int idleTimeout = 600000;				// 10 minutes
    int connectionTimeout = 3000;			// 3 seconds
    int initializationFailTimeout = 3000;   // 3 seconds
    String connectionTestQuery;
    int validationTimeout = 1000;			// 1 seconds
    boolean cachePrepStmts = true;
    int prepStmtCacheSize = 300;
    int prepStmtCacheSqlLimit = 2048;
    boolean useServerPrepStmts = true;
    boolean useLocalSessionState = true;
    boolean cacheResultSetMetadata = true;
    boolean cacheServerConfiguration = true;
    boolean elideSetAutoCommits = true;
    boolean maintainTimeStats = false;
    boolean rewriteBatchedStatements = false;
    boolean readOnly = false;
    boolean autoCommit = true;
}
