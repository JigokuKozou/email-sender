package ru.shchelkin.emailsender.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.shchelkin.emailsender.models.EmailMessage;

import static ru.shchelkin.emailsender.configs.RabbitMqConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitEmailMessageService implements EmailMessageService{

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final RabbitTemplate template;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMessage(EmailMessage emailMessage) {
        try {
            template.setExchange(EMAIL_MESSAGES_EXCHANGE);
            template.convertAndSend(EMAIL_MESSAGES_ROUTING_KEY_SIMPLE, emailMessage);
            log.info(String.format("Message for \"%s\" accepted for sending", emailMessage.destinationEmail()));
        }
        catch (RuntimeException e) {
            String exceptionMessage = String.format("Failed to accept message submission for \"%s\"", emailMessage.destinationEmail());

            log.error(exceptionMessage);
            throw new RuntimeException(exceptionMessage, e);
        }
    }

    @RabbitListener(queues = EMAIL_MESSAGES_QUEUE_SIMPLE)
    public void processSendMessage(EmailMessage emailMessage) {
        try {
            //javaMailSender.send(getSimpleMessage(emailMessage));
            log.info(String.format("Message for \"%s\" is send", emailMessage.destinationEmail()));
        }
        catch (RuntimeException e) {
            String exceptionMessage = String.format("Failed to send message for \"%s\"", emailMessage.destinationEmail());

            log.error(exceptionMessage);
            throw new RuntimeException(exceptionMessage, e);
        }
    }

    private SimpleMailMessage getSimpleMessage(EmailMessage emailMessage) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(emailMessage.destinationEmail());
        message.setSubject(emailMessage.header());
        message.setText(emailMessage.body());

        return message;
    }
}
