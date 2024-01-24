package com.temporal.demos.temporalspringbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemoWorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoWorkerApplication.class, args).start();
    }
}
