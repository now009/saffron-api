package com.saffron.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.saffron.portal.mapper")
public class SaffronApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaffronApiApplication.class, args);
    }
}
