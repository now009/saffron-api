package com.saffron.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.saffron")
@MapperScan({"com.saffron.portal.mapper", "com.saffron.eai.mapper", "com.saffron.qbank.mapper"})
@EnableRetry
@EnableScheduling
public class SaffronApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaffronApiApplication.class, args);
    }
}
