package com.example.dp.global.infra.mail.service.impl;

import com.example.dp.global.infra.mail.service.MailService;
import com.example.dp.global.redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;

    private static final int DURATION = 30;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public String createAuthCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public void sendMessage(String to, String subject) {
        try {
            String code = createAuthCode();
            MimeMessage message = createMessage(to, subject, code);

            if (redisUtil.hasMail(to)) {
                redisUtil.deleteMail(to);
            }
            redisUtil.addMailList(to, code, DURATION);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.warn(e.getMessage());
        }
    }

    public boolean checkCode(String to, String code) {
        Object authCode = redisUtil.getCode(to);

        return authCode.equals(code);
    }

    private MimeMessage createMessage(String to, String subject, String code)
        throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(email);
        message.addRecipients(RecipientType.TO, to);
        message.setSubject(subject);
        message.setText(createMailHtml(code), "UTF-8", "html");

        return message;
    }

    private String createMailHtml(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context);
    }
}
