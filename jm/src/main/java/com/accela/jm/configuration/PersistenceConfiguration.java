/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.configuration;

import com.accela.jm.contact.db.AddressRepository;
import com.accela.jm.contact.db.PersonRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(PersistenceProperties.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { PersonRepository.class, AddressRepository.class },
        entityManagerFactoryRef = PersistenceConfiguration.ENTITY_MANAGER_FACTORY_REF)
public class PersistenceConfiguration {

    public static final String ENTITY_MANAGER_FACTORY_REF = "jmEntityManagerFactory";

    @Bean(ENTITY_MANAGER_FACTORY_REF)
    LocalContainerEntityManagerFactoryBean jmEntityManagerFactory(DataSource dataSource,
                                                                  PersistenceProperties properties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.accela.jm");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", properties.getDialect());
        jpaProperties.put("hibernate.hbm2ddl.auto", properties.getHbm2ddlAuto());
        jpaProperties.put("hibernate.ejb.naming_strategy", properties.getEjbNamingStrategy());
        jpaProperties.put("hibernate.show_sql", properties.getShowSql());
        jpaProperties.put("hibernate.format_sql", properties.getFormatSql());

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    DataSource dataSource(PersistenceProperties properties) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getDriver());
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        return new HikariDataSource(config);
    }

    @Bean
    public JpaTransactionManager transactionManager(@Qualifier(ENTITY_MANAGER_FACTORY_REF) LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/changeLog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
