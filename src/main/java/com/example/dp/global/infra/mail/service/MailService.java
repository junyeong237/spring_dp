package com.example.dp.global.infra.mail.service;

import jakarta.mail.MessagingException;

public interface MailService {

    String createAuthCode();
    void sendMessage(String to, String subject) throws MessagingException;

}
