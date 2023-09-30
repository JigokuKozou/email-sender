package ru.shchelkin.emailsender.services;

import ru.shchelkin.emailsender.models.EmailMessage;

public interface EmailMessageService {

    void sendMessage(EmailMessage emailMessage);
}
