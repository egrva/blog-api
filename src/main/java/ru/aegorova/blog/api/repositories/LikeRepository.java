package ru.aegorova.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aegorova.blog.api.models.Like;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> getAllByUser_Id(Long user_id);
}
