package com.binas.tachographcms.service.impl;

import com.binas.tachographcms.model.entity.Email;
import com.binas.tachographcms.repository.EmailRepository;
import com.binas.tachographcms.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private EmailRepository emailRepository;

    public EmailServiceImpl(@Qualifier("getJavaMailSender") JavaMailSender emailSender, EmailRepository emailRepository) {
        this.emailSender = emailSender;
        this.emailRepository = emailRepository;
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, byte[] file, String filename) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@tacho.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment(filename + ".ddd", new ByteArrayResource(file));

        emailSender.send(message);
    }

    @Override
    public String getEmail() {
        Optional<Email> email = this.emailRepository.getEmailById(1L);
        if(email.isPresent()) {
            return email.get().getEmail();
        } else {
            return "";
        }
    }

    @Override
    public void setEmail(String email) {
        Email mail = new Email(1L, email);
        this.emailRepository.save(mail);
    }
}
