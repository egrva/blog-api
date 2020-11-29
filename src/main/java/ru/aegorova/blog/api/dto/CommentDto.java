package ru.aegorova.blog.api.dto;

import lombok.Builder;
import lombok.Data;
import ru.aegorova.blog.api.models.Comment;

@Data
@Builder
public class CommentDto {
    private Long id;
    private Long userId;
    private Long postId;
    private String text;

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .userId(comment.getUser().getId())
                .postId(comment.getPost().getId())
                .text(comment.getText())
                .id(comment.getId())
                .build();
    }
}
