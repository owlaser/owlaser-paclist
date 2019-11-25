package com.owlaser.paclist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class PaclistApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaclistApplication.class, args);
    }

}