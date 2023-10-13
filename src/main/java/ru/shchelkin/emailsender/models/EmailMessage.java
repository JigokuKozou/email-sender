package ru.shchelkin.emailsender.models;

import java.io.Serializable;

public record EmailMessage(String destinationEmail, String header, String body) implements Serializable {
}
