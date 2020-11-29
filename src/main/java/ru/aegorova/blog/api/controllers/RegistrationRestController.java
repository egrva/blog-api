package ru.aegorova.blog.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aegorova.blog.api.dto.RegistrationDto;
import ru.aegorova.blog.api.services.RegistrationService;


@RestController
@RequestMapping("/api/registration")
public class RegistrationRestController {

    private final RegistrationService registrationService;

    public RegistrationRestController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody RegistrationDto form) {
        registrationService.registration(form);
        return ResponseEntity.accepted().build();
    }
}

