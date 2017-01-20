package com.kricko;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(LocalWomenApplication.class, args);
    }
}
