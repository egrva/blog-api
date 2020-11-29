package ru.aegorova.blog.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aegorova.blog.api.dto.UserDto;
import ru.aegorova.blog.api.security.details.UserDetailsImpl;
import ru.aegorova.blog.api.services.UserService;


@RestController
@RequestMapping("/api/profile")
public class UserRestController {

    final
    UserService usersService;

    public UserRestController(UserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getSelf(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.of(usersService.getUserByEmail(userDetails.getUsername()));
    }
}
