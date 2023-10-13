package ru.shchelkin.emailsender.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.shchelkin.emailsender.models.EmailMessage;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockingEmailMessageService implements EmailMessageService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;
    
    @Override
    public void sendMessage(@NonNull EmailMessage emailMessage) {
        if (Strings.isBlank(emailMessage.destinationEmail()))
            throw new IllegalArgumentException("destinationEmail cannot be blank");

        if (Strings.isBlank(emailMessage.header()) && Strings.isBlank(emailMessage.header()))
            throw new IllegalArgumentException("message cannot be empty");

        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(emailMessage.destinationEmail());
            message.setSubject(emailMessage.header());
            message.setText(emailMessage.body());

            javaMailSender.send(message);
            log.info("Message is sent to " + Arrays.toString(message.getTo()));
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to send message", e);
        }
    }
}
