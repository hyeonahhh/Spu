package com.example.spu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpuApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpuApplication.class, args);
    }

}
