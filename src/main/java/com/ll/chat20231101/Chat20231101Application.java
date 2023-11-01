package com.ll.chat20231101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Chat20231101Application {

    public static void main(String[] args) {
        SpringApplication.run(Chat20231101Application.class, args);
    }

}
