package ru.aegorova.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aegorova.blog.api.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByPost_Id(Long post_id);
}
