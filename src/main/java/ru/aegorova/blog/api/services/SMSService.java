package ru.aegorova.blog.api.services;
public interface SMSService {
    void sendSms(String phoneNumber, String text);
}
