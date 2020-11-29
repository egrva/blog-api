package ru.aegorova.blog.api.services;

public interface EmailService {
    void sendMail(String subject, String text, String email);
}
