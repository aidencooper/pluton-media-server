package net.aidencooper.pluton.mediaserver.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Getter
public class SQLiteConfig {
    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;

    public SQLiteConfig(PlatformConfig platformConfig, @Value("${spring.datasource.username}") String username, @Value("${spring.datasource.password}") String password) {
        this.driverClassName = "org.sqlite.JDBC";
        this.url = "jdbc:sqlite:" + platformConfig.getPlutonConfigPath() + "/pluton.db";
        this.username = username;
        this.password = password;
    }

    @Bean
    public DataSource dataSource(PlatformConfig platformConfig) {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:" + platformConfig.getPlutonConfigPath() + "/pluton.db");
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        return dataSource;
    }
}
