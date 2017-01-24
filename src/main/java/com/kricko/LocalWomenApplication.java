package com.kricko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("applicationContext.xml")
@PropertySource({ "config/application.properties",
                  "config/dev.properties",
                  "config/home.properties"})
public class LocalWomenApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalWomenApplication.class, args);
    }
}
