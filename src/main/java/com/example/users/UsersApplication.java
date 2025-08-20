package com.example.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableCaching
@SpringBootApplication
public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
