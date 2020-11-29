package ru.aegorova.blog.api.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.aegorova.blog.api.dto.UserDto;
import ru.aegorova.blog.api.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::from).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long userId) {
        return UserDto.from(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cant't find user with id: " + userId)));
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return Optional.of(UserDto.from(userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("User not found"))));
    }
}
