package com.travelapp.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.travelapp"})
@ComponentScan(basePackages = {"com.travelapp"})
@EnableJpaRepositories(basePackages = {"com.travelapp"})
@EnableJpaAuditing
public class JauntsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JauntsApplication.class, args);
    }
}
