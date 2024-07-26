package com.citycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CityCareApplication {
//    @Async
    public static void main(String[] args) {
        SpringApplication.run(CityCareApplication.class, args);
    }
}
