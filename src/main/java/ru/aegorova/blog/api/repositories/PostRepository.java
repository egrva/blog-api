package ru.aegorova.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aegorova.blog.api.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select post from Post post inner join post.user u where u.id = :userId")
    List<Post> findAllFavoriteByUserId(@Param("userId") Long userId);
}
