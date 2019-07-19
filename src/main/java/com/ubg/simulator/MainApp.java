package com.ubg.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/***********
 * @author bruce
 * */
@SpringBootApplication
@EnableConfigurationProperties
public class MainApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApp.class, args);
    }
}
