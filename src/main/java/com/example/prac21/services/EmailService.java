package com.example.prac21.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {


    public void sendEmail(String toEmail,String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.yandex.ru");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.port",465);
        properties.put("mail.smtp.auth","true");
        String user = "p.saprykinxxx@yandex.ru";
        String pass = "kedtohyzenrkhktn";
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,pass);
            }
        });

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("p.saprykinxxx@yandex.ru");
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(subject);
        msg.setText(body);
        msg.setSentDate(new Date());
        Transport.send(msg);
        System.out.println("senT!");
    }
}
