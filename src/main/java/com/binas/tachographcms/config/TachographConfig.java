package com.binas.tachographcms.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class TachographConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("hostingwp2068558.online.pro");
        mailSender.setPort(465);
        mailSender.setDefaultEncoding("UTF-8");

        mailSender.setUsername("tacho@imakeable.com");
        mailSender.setPassword("ak_CS3bJ");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
