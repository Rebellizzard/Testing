package com.kiluah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CoffeeApplication {

    public static void main(String[] args) {
        run();
        SpringApplication.run(CoffeeApplication.class, args);
    }
    
    public static void run(){
        String encrypted = new BCryptPasswordEncoder().encode("kil");
        System.out.println(encrypted);
    }
}

