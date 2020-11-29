package ru.aegorova.blog.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aegorova.blog.api.dto.LoginDto;
import ru.aegorova.blog.api.dto.TokenDto;
import ru.aegorova.blog.api.services.LoginTokenService;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    private final LoginTokenService loginTokenService;

    public LoginRestController(LoginTokenService loginTokenService) {
        this.loginTokenService = loginTokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> signIn(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(loginTokenService.login(loginDto));
    }

}
