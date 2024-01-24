package com.idanshal.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemoTemporalWorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoTemporalWorkerApplication.class, args).start();
    }
}
