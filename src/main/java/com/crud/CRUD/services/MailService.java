package com.crud.CRUD.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void email(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        //setear valores
        message.setFrom("cuentadeplay2468@gmail.com"); //nuestro email
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        //ENVIAMOS
        javaMailSender.send(message);
    }
}
