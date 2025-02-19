package com.jarapplication.kiranastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

@SpringBootApplication
public class KiranaStoreApplication {

    public static void main(String[] args) {
        System.out.println(HttpStatus.CREATED.value());
//        SpringApplication.run(KiranaStoreApplication.class, args);
    }

}
