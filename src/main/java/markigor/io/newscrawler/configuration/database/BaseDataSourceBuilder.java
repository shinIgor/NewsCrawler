package markigor.io.newscrawler.configuration.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
public record BaseDataSourceBuilder(BaseDataSourceProperties properties) {
    private static final String DEFAULT_POOL_NAME_PREFIX = "CONNECTION-POOL-";

    public BaseDataSourceBuilder(@NonNull BaseDataSourceProperties properties) {
        this.properties = properties;
    }

    private String getPoolName(String name) {
        return (DEFAULT_POOL_NAME_PREFIX + name).toUpperCase();
    }

    private HikariConfig getConfig() {
        HikariConfig config = new HikariConfig();
        config.setPoolName(getPoolName(properties.getName()));
        config.setDriverClassName(properties.getDriverClassName());
        config.setJdbcUrl(properties.getJdbcUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        config.setMinimumIdle(properties.getMinimumIdle());
        config.setMaximumPoolSize(properties.getMaximumPoolSize());
        config.setMaxLifetime(properties.getMaxLifetime());
        config.setIdleTimeout(properties.getIdleTimeout());
        config.setConnectionTimeout(properties.getConnectionTimeout());
        config.setInitializationFailTimeout(properties.getInitializationFailTimeout());
        config.setConnectionTestQuery(properties.getConnectionTestQuery());
        config.setValidationTimeout(properties.getValidationTimeout());
        config.addDataSourceProperty("cachePrepStmts", properties.isCachePrepStmts());
        config.addDataSourceProperty("prepStmtCacheSize", properties.getPrepStmtCacheSize());
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getPrepStmtCacheSqlLimit());
        config.addDataSourceProperty("useServerPrepStmts", properties.isUseServerPrepStmts());
        config.addDataSourceProperty("useLocalSessionState", properties.isUseLocalSessionState());
        config.addDataSourceProperty("cacheResultSetMetadata", properties.isCacheResultSetMetadata());
        config.addDataSourceProperty("cacheServerConfiguration", properties.isCacheServerConfiguration());
        config.addDataSourceProperty("elideSetAutoCommits", properties.isElideSetAutoCommits());
        config.addDataSourceProperty("maintainTimeStats", properties.isMaintainTimeStats());
        config.setReadOnly(properties.isReadOnly());
        config.setAutoCommit(properties.isAutoCommit());

        return config;
    }




    public DataSource build() {
        return new HikariDataSource(getConfig());
    }
}
