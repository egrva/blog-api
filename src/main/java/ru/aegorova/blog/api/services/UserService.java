package ru.aegorova.blog.api.services;


import ru.aegorova.blog.api.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUser(Long userId);

    Optional<UserDto> getUserByEmail(String email);

}
