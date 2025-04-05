package com.example.cabfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CabFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CabFinderApplication.class, args);
        System.out.println("Server Started te port 8080 ");
    }
}
