package ru.aegorova.blog.api.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.aegorova.blog.api.dto.RegistrationDto;
import ru.aegorova.blog.api.models.FileInfo;
import ru.aegorova.blog.api.models.Role;
import ru.aegorova.blog.api.models.State;
import ru.aegorova.blog.api.models.User;
import ru.aegorova.blog.api.repositories.FileInfoRepository;
import ru.aegorova.blog.api.repositories.UserRepository;
import ru.aegorova.blog.api.util.FileStorageUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Component
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ExecutorService threadPool;
    private final PasswordEncoder passwordEncoder;
    private final Configuration configuration;
    private final SMSService smsService;
    private final FileStorageUtil fileStorageUtil;
    private final FileInfoRepository fileInfoRepository;

    public RegistrationServiceImpl(UserRepository userRepository
            , EmailService emailService
            , ExecutorService threadPool
            , PasswordEncoder passwordEncoder
            , Configuration configuration
            , SMSService smsService
            , FileStorageUtil fileStorageUtil
            , FileInfoRepository fileInfoRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.threadPool = threadPool;
        this.passwordEncoder = passwordEncoder;
        this.configuration = configuration;
        this.smsService = smsService;
        this.fileStorageUtil = fileStorageUtil;
        this.fileInfoRepository = fileInfoRepository;
    }


    @Override
    public void registration(RegistrationDto form) {
        String raw = form.getPassword();
        String encoded = passwordEncoder.encode(raw);

        userRepository.findByEmail(form.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("Пользователь с данной почтой уже зарегестрирован");
        });

        FileInfo avatarFile = FileInfo.builder()
                .originalFileName("avi.png")
                .storageFileName("avi.png")
                .url(fileStorageUtil.getStoragePath() + "/" + "avi.png")
                .size(fileStorageUtil.sizeOf(fileStorageUtil.getStoragePath() + "/" + "avi.png"))
                .type("image/png")
                .build();

        fileInfoRepository.save(avatarFile);

        User user = User.builder()
                .email(form.getEmail())
                .phoneNumber(form.getPhoneNumber())
                .name(form.getName())
                .lastName(form.getLastName())
                .password(encoded)
                .state(State.NOT_CONFIRMED)
                .confirmCode(UUID.randomUUID().toString())
                .role(Role.USER)
                .avatar(avatarFile)
                .build();

        String link = "http://localhost:8080/confirm/" + user.getConfirmCode();
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("link", link);

        try {
            stringBuilder.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(configuration.getTemplate("index.txt"), model));

        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        threadPool.submit(() -> {
            emailService.sendMail("Регистрация"
                    , stringBuilder.toString()
                    , user.getEmail());
        });

        threadPool.submit(() -> {
            String text = "Вы зарегестрированы!!!!! Congratulations , ваш e-mail: " + user.getEmail();
//            smsService.sendSms(user.getPhoneNumber(), text); - денег больше нет((
        });

        userRepository.save(user);
    }
}
