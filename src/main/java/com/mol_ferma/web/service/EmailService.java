package com.mol_ferma.web.service;


import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage email);
    void sendEmailMessage(String emailTo, String subject, String message);
}
