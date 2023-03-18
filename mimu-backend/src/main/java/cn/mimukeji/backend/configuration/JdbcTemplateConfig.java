package cn.mimukeji.backend.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    @Bean(name = "monitorsqliteJdbcTemplate")
    JdbcTemplate monitorsqliteJdbcTemplate(@Qualifier("monitorsqliteDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
