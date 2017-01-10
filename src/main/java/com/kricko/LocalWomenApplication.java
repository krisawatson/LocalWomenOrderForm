package com.kricko;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("applicationContext.xml")
public class LocalWomenApplication {

    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.debug ("Spring Boot runner");
        SpringApplication.run(LocalWomenApplication.class, args);
    }
}
