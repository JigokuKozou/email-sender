package ru.shchelkin.emailsender.configs;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EMAIL_MESSAGES_EXCHANGE = "email.messages.exchange";
    public static final String EMAIL_MESSAGES_QUEUE_SIMPLE = "email.messages.simple";
    public static final String EMAIL_MESSAGES_ROUTING_KEY_SIMPLE = "simple";

    @Bean
    public Queue emailMessagesQueue() {
        return new Queue(EMAIL_MESSAGES_QUEUE_SIMPLE);
    }

    @Bean
    public Exchange emailMessageExchange() {
        return new DirectExchange(EMAIL_MESSAGES_EXCHANGE);
    }

    @Bean
    public Binding emailMessageBinding() {
        return BindingBuilder
                .bind(emailMessagesQueue()).to(emailMessageExchange()).with(EMAIL_MESSAGES_ROUTING_KEY_SIMPLE)
                .noargs();
    }
}
