package ru.aegorova.blog.api.services;

import org.springframework.stereotype.Service;
import ru.aegorova.blog.api.models.State;
import ru.aegorova.blog.api.models.User;
import ru.aegorova.blog.api.repositories.UserRepository;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    private final UserRepository usersRepository;

    public ConfirmServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void confirm(String confirmCode) {
        User user = usersRepository.findByConfirmCode(confirmCode)
                .orElseThrow(() -> new IllegalArgumentException("Can't find user by confirm code: " + confirmCode));
        user.setState(State.CONFIRMED);
        usersRepository.save(user);
    }
}
