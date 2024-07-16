package com.lecture8.EmployeeCRUD.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${db1.url}")
    private String db1Url;

    @Value("${db1.username}")
    private String db1Username;

    @Value("${db1.password}")
    private String db1Password;

    @Value("${db2.url}")
    private String db2Url;

    @Value("${db2.username}")
    private String db2Username;

    @Value("${db2.password}")
    private String db2Password;

    @Bean(name = "dataSource1")
    public DataSource dataSource1() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(db1Url);
        dataSourceBuilder.username(db1Username);
        dataSourceBuilder.password(db1Password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "dataSource2")
    public DataSource dataSource2() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(db2Url);
        dataSourceBuilder.username(db2Username);
        dataSourceBuilder.password(db2Password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "jdbcTemplate1")
    public JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource1) {
        return new JdbcTemplate(dataSource1);
    }

    @Bean(name = "jdbcTemplate2")
    public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource2) {
        return new JdbcTemplate(dataSource2);
    }
}
