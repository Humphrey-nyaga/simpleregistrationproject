package com.example.simpleregistrationproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.simpleregistrationproject")
public class SimpleprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleprojectApplication.class, args);
    }

}
