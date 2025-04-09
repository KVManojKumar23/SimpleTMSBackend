package com.snipe.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class DataSourceShutdown {

    @Autowired
    private DataSource dataSource;

    @PreDestroy
    public void closeDataSource() throws Exception {
        if (dataSource instanceof com.zaxxer.hikari.HikariDataSource) {
            ((com.zaxxer.hikari.HikariDataSource) dataSource).close();
        }
    }
}
