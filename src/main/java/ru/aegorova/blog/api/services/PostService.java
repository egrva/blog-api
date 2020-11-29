package ru.aegorova.blog.api.services;

import ru.aegorova.blog.api.dto.CommentDto;
import ru.aegorova.blog.api.dto.LikeDto;
import ru.aegorova.blog.api.dto.PostDto;


import java.util.List;
import java.util.Optional;

public interface PostService {
    List<CommentDto> commentsToPost(Long post_id);
    PostDto getOne(Long id);
    void makeComment(CommentDto form);
    void makeLike(LikeDto form);
    List<PostDto> getFavoritePosts(Long userId);
    List<PostDto> getAll();
}
