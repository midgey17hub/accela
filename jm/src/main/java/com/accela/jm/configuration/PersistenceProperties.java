/*
 * // *******************************************
 * // * Copyright (c) CRIF - All Right Reserved *
 * // *******************************************
 *
 */
package com.accela.jm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

@Configuration
@PropertySource(value = "classpath:connection.properties")
public class PersistenceProperties {

    private final String dialect;
    private final String hbm2ddlAuto;
    private final String ejbNamingStrategy;
    private final String showSql;
    private final String formatSql;
    private final String username;
    private final String password;
    private final String url;
    private final String driver;

    public PersistenceProperties(
            @Value("${jm.hibernate.dialect:org.hibernate.dialect.Oracle10gDialect}") String dialect,
            @Value("${jm.hibernate.hbm2ddl.auto:none}") String hbm2ddlAuto,
            @Value("${jm.hibernate.ejb.naming_strategy:implicit_naming_strategy}") String ejbNamingStrategy,
            @Value("${jm.hibernate.show_sql:false}") String showSql,
            @Value("${jm.hibernate.format_sql:false}") String formatSql,
            @Value("${jm.db.user}") String username,
            @Value("${jm.db.password}") String password,
            @Value("${jm.db.url}") String url,
            @Value("${jm.db.driver}") String driver) {
        this.dialect = dialect;
        this.hbm2ddlAuto = hbm2ddlAuto;
        this.ejbNamingStrategy = ejbNamingStrategy;
        this.showSql = showSql;
        this.formatSql = formatSql;
        this.username = username;
        this.password = password;
        this.url = url;
        this.driver = driver;
    }

    public String getDialect() {
        return dialect;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public String getEjbNamingStrategy() {
        return ejbNamingStrategy;
    }

    public String getShowSql() {
        return showSql;
    }

    public String getFormatSql() {
        return formatSql;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersistenceProperties that = (PersistenceProperties) o;
        return Objects.equals(dialect, that.dialect) && Objects.equals(hbm2ddlAuto, that.hbm2ddlAuto) && Objects.equals(ejbNamingStrategy, that.ejbNamingStrategy)
                && Objects.equals(showSql, that.showSql) && Objects.equals(formatSql, that.formatSql) && Objects.equals(username, that.username) && Objects.equals(
                password,
                that.password) && Objects.equals(url, that.url) && Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dialect, hbm2ddlAuto, ejbNamingStrategy, showSql, formatSql, username, password, url, driver);
    }

    @Override
    public String toString() {
        return "PersistenceProperties{" +
                "dialect='" + dialect + '\'' +
                ", hbm2ddlAuto='" + hbm2ddlAuto + '\'' +
                ", ejbNamingStrategy='" + ejbNamingStrategy + '\'' +
                ", showSql='" + showSql + '\'' +
                ", formatSql='" + formatSql + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                '}';
    }
}
