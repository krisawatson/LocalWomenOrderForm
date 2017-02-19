package com.kricko;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@PropertySource({ "classpath:config/application.yaml",
                  "classpath:config/dev.yaml",
                  "classpath:config/home.yaml"})
public class LocalWomenApplication extends SpringBootServletInitializer implements InitializingBean {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(LocalWomenApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<LocalWomenApplication> applicationClass = LocalWomenApplication.class;

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
