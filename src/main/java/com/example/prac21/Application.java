package com.example.prac21;

import com.example.prac21.services.EmailService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import javax.mail.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Application {


    public static void main(String[] args) throws IOException, MessagingException {
//        FileInputStream file = new FileInputStream("mail.properties");
//        Properties properties = new Properties();
//        properties.load(file);
//        String user = properties.getProperty("mail.user");
//        String password = properties.getProperty("mail.password");
//        String host = properties.getProperty("mail.host");
//        Properties prop = new Properties();
//        prop.put("mail.store.protocol","imaps");
//        Store store = Session.getInstance(prop).getStore();
//        store.connect(host,user,password);
//        Folder inbox = store.getFolder("self");
//        inbox.open(Folder.READ_ONLY);
//        System.out.println(inbox.getMessageCount());
        EmailService emailService = new EmailService();
        emailService.sendEmail("paulbwbf@yandex.ru","asd","helllol!");

    }
}
