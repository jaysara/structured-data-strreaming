package com.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

@ComponentScan("com.rsvps.controller")
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {                
                
        SpringApplication.run(Application.class, args);                               
    }


}
