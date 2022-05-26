package com.example.prac21;

import com.example.prac21.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Prac21Application {
    @Autowired
    private static EmailService service;

    public static void main(String[] args) {
//        sendMail();
        SpringApplication.run(Prac21Application.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public static void sendMail(){
//        service.sendEmail("ps25072001@gmail.com","subj","hello!");
//    }
}
