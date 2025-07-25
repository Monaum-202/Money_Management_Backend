package com.monaum.Money_Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class MoneyMangementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyMangementApplication.class, args);
    }

}
