package ru.aegorova.blog.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aegorova.blog.api.services.ConfirmService;

@RestController
@RequestMapping("/api/confirm")
public class ConfirmRestController {

    private final ConfirmService confirmService;

    public ConfirmRestController(ConfirmService confirmService) {
        this.confirmService = confirmService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> confirm(@PathVariable("token") String token) {
        if (token != null) {
            confirmService.confirm(token);
            return ResponseEntity.accepted().build();
        }
        throw new IllegalArgumentException("Token is empty");
    }
}
