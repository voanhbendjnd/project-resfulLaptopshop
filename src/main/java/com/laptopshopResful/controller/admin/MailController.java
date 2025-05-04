package com.laptopshopResful.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.service.EmailService;
import com.laptopshopResful.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/mails")
public class MailController {
    public final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    @ApiMessage("Send email")
    public ResponseEntity<Void> sendMail() {
        this.emailService.senSimpleEmail();
        return ResponseEntity.ok(null);
    }
}
