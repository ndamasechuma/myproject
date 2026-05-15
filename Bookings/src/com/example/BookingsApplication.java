package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BookingsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingsApplication.class, args);
    }
}


