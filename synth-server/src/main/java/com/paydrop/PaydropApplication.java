package com.paydrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
//@ComponentScan(basePackages = {"com.paydrop", "com.paydrop.config"})
public class PaydropApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaydropApplication.class, args);
    }
}
