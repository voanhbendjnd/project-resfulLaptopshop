package com.laptopshopResful.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {
    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(
            MailSender mailSender,
            JavaMailSender javaMailSender,
            SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void senSimpleEmail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("benva.ce190709@gmail.com");
        msg.setSubject("Test test");
        msg.setText("Anh try say hi");
        this.mailSender.send(msg);
    }

    @Async
    public void sendOtp(int otp, String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Otp for forget password");
        msg.setText(otp + "");
        this.mailSender.send(msg);
    }
}
