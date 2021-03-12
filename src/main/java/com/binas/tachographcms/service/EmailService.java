package com.binas.tachographcms.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMessageWithAttachment(String to, String subject, String text, byte[] file, String filename) throws MessagingException;

    String getEmail();

    void setEmail(String email);
}
