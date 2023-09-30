package ru.shchelkin.emailsender.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.shchelkin.emailsender.models.EmailMessage;
import ru.shchelkin.emailsender.services.EmailMessageService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailMessageController {

    private final EmailMessageService emailMessageService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void sendMessage(@RequestBody EmailMessage emailMessage) {
        emailMessageService.sendMessage(emailMessage);
    }
}
