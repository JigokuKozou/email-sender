package ru.shchelkin.emailsender.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.shchelkin.emailsender.models.EmailMessage;
import ru.shchelkin.emailsender.services.EmailMessageService;

@Tag(name = "Email message controller", description = "Controller for sending messages")
@RestController
public class EmailMessageController {

    private final EmailMessageService emailMessageService;

    @Autowired
    public EmailMessageController(@Qualifier("rabbitEmailMessageService") EmailMessageService emailMessageService) {
        this.emailMessageService = emailMessageService;
    }

    @PostMapping
    @Operation(summary = "Send text message")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@RequestBody EmailMessage emailMessage) {
        emailMessageService.sendMessage(emailMessage);
    }
}
