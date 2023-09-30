package ru.shchelkin.emailsender.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {

    private String destinationEmail;

    private String header;

    private String body;
}
