package com.nnk.springboot.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PoseidonUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PoseidonUiApplication.class, args);
    }
}