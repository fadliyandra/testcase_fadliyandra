package com.example.casefitnesscenter.service.impl;


import com.example.casefitnesscenter.common.exception.AppRuntimeException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j

public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender,
                        SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmailFromHtmlTemplate(String sendTo,
                                          String subject,
                                          String templateName,
                                          Context context) {
        try {
            String body = templateEngine.process(templateName, context);

            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, "utf-8");
            helper.setFrom("fitness@gmail.com", "Fit World");
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mail);
            log.info("Success to sent message with {} to {} from {}", subject, sendTo, "fitness@gmail.com");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.info("Error while send email {}", e.getCause().toString());
            throw new AppRuntimeException(e.getMessage(), e.getCause());
        }
    }
}
