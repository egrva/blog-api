package ru.aegorova.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aegorova.blog.api.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user left join user.avatar as avatar where user.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByConfirmCode(String token);

}


