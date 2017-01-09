package com.kricko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("applicationContext.xml")
public class LocalWomenApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalWomenApplication.class, args);
    }
}
