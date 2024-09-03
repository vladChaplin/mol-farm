package com.mol_ferma.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private boolean debug;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smptAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean smptStarttlsEnable;

    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean smtpStarttlsRequired;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", protocol);
        properties.put("mail.smtp.auth", smptAuth);
        properties.put("mail.smtp.starttls.enable", smptStarttlsEnable);
        properties.put("mail.smtp.starttls.required", smtpStarttlsRequired);
        properties.put("mail.debug", debug);

        return mailSender;
    }
}
