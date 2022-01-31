package com.hanghae.hanghaespringv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeSpringV2Application {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeSpringV2Application.class, args);
    }

}
