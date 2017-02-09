package com.kricko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@PropertySource({ "classpath:config/application.properties",
                  "classpath:config/dev.yaml",
                  "classpath:config/home.properties"})
public class LocalWomenApplication extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LocalWomenApplication.class);
     }

     public static void main(String[] args) throws Exception {
        SpringApplication.run(LocalWomenApplication.class, args);
     }
}
