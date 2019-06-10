package com.higgs.network.wallet.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * main class for test
 * 1. exclude DataSourceAutoConfiguration - 避免自动寻找导致启动出错
 *
 * @author Alex Wu
 * @date 2018-4-11
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan("com.higgs.network.wallet.dao")
@ComponentScan("com.higgs.network.wallet")
public class BootLessonApplication implements TransactionManagementConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BootLessonApplication.class, args);
    }
    @Autowired
    DataSource dataSource;

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


}