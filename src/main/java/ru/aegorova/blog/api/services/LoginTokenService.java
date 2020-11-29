package ru.aegorova.blog.api.services;


import ru.aegorova.blog.api.dto.LoginDto;
import ru.aegorova.blog.api.dto.TokenDto;

public interface LoginTokenService {
    TokenDto login(LoginDto loginDto);
}
