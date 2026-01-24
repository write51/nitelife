/*

package com.nitelife.demo;

import java.io.IOException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class NitelifeMvcAppConfig implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(NitelifeMvcAppConfig.class);


    private ApplicationContext applicationContext;


    public NitelifeMvcAppConfig() {
        super();
    }


    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean
    public DataSource dataSource() {
        try {

            final Resource dbResource = this.applicationContext.getResource("classpath:data/chinook.sqlite");
            logger.debug("Database path: " + dbResource.getURL().getPath());

            final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName("org.sqlite.JDBC");
            dataSourceBuilder.url("jdbc:sqlite:" + dbResource.getURL().getPath());
            return dataSourceBuilder.build();

        } catch (final IOException e) {
            throw new ApplicationContextException("Error initializing database", e);
        }
    }

}
*/
