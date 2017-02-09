package com.kricko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@PropertySource({ "classpath:config/application.yaml",
                  "classpath:config/dev.yaml",
                  "classpath:config/home.yaml"})
public class LocalWomenApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(LocalWomenApplication.class, args);
    }
}
