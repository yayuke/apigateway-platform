package com.apigateway.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * API管理后台启动类
 *
 * @author apigateway
 * @since 1.0.0
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
@ComponentScan(basePackages = "com.apigateway")
public class ManagerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ManagerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
