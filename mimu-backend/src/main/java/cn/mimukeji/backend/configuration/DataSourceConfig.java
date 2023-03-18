package cn.mimukeji.backend.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "monitorsqliteDataSource")
    @Qualifier("monitorsqliteDataSource")
    @ConfigurationProperties(prefix = "spring.multidatasource.monitorsqlite")
    public DataSource monitorsqliteDataSource() {
        return DataSourceBuilder.create().build();
    }

}