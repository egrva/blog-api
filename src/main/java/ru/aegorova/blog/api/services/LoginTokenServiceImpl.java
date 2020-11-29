package ru.aegorova.blog.api.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aegorova.blog.api.dto.LoginDto;
import ru.aegorova.blog.api.dto.TokenDto;
import ru.aegorova.blog.api.models.State;
import ru.aegorova.blog.api.models.User;
import ru.aegorova.blog.api.repositories.UserRepository;

import java.util.Optional;

@Service
public class LoginTokenServiceImpl implements LoginTokenService {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    public LoginTokenServiceImpl(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        // получаем пользователя по его email
        Optional<User> userOptional = usersRepository.findByEmail(loginDto.getEmail());
        // если у меня есть этот пользвователь
        if (userOptional.isPresent()) {
            // получаем его
            User user = userOptional.get();
            // если пароль подходит
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                // создаем токен
                String token = Jwts.builder()
                        .setSubject(user.getId().toString()) // id пользователя
                        .claim("login", user.getEmail()) // имя
                        .claim("state", user.getState().name()) // роль
                        .signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
                        .compact(); // преобразовали в строку
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");
    }
}
