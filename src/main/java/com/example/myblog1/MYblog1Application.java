package com.example.myblog1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MYblog1Application {

    public static void main(String[] args) {
        SpringApplication.run(MYblog1Application.class, args);
    }

}
