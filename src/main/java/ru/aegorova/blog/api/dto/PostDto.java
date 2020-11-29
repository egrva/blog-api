package ru.aegorova.blog.api.dto;

import lombok.Builder;
import lombok.Data;
import ru.aegorova.blog.api.models.Post;

@Data
@Builder
public class PostDto {
    private Long id;
    private String header;
    private String text;

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .header(post.getHeader())
                .text(post.getText())
                .build();
    }
}
