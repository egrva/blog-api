package ru.aegorova.blog.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;

@Service
public class SMSServiceImpl implements SMSService {

    private final ExecutorService threadPool;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${sms.aero.email}")
    private String smsAeroEmail;

    @Value("${sms.aero.api-key}")
    private String smsAeroApiKey;

    @Value("${sms.aero.from}")
    private String smsAeroFrom;

    @Value("${sms.aero.type}")
    private String smsAeroType;

    @Value("${sms.aero.url}")
    private String smsAeroUrl;

    public SMSServiceImpl(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void sendSms(String phoneNumber, String text) {

        threadPool.submit(() -> {
            String request = smsAeroUrl + "?user="
                    + smsAeroEmail + "&password="
                    + smsAeroApiKey + "&to="
                    + phoneNumber +
                    "&text=" + text
                    + "&from="
                    + smsAeroFrom + "&type="
                    + smsAeroType;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(request, String.class);
            if (responseEntity.getBody().contains("accepted")) {
                return true;
            } else {
                throw new IllegalArgumentException("Incorrect phone number");
            }
        });
    }
}




