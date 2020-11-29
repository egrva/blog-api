package ru.aegorova.blog.api.dto;

import lombok.Builder;
import lombok.Data;
import ru.aegorova.blog.api.models.Like;

@Data
@Builder
public class LikeDto {
    private Long id;
    private Long userId;
    private Long postId;

    public static LikeDto from(Like like) {
        return LikeDto.builder()
                .id(like.getId())
                .postId(like.getPost().getId())
                .userId(like.getUser().getId())
                .build();
    }

}
