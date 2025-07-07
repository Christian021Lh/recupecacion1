package com.example.clhventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClhVentasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClhVentasApplication.class, args);
    }

}
