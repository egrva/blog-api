package ru.aegorova.blog.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aegorova.blog.api.dto.CommentDto;
import ru.aegorova.blog.api.dto.LikeDto;
import ru.aegorova.blog.api.dto.PostDto;
import ru.aegorova.blog.api.models.Comment;
import ru.aegorova.blog.api.models.Like;
import ru.aegorova.blog.api.repositories.CommentRepository;
import ru.aegorova.blog.api.repositories.LikeRepository;
import ru.aegorova.blog.api.repositories.PostRepository;
import ru.aegorova.blog.api.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private LikeRepository likeRepository;


    @Override
    public List<CommentDto> commentsToPost(Long post_id) {
        return commentRepository.getAllByPost_Id(post_id).stream().map(
                CommentDto::from
        ).collect(Collectors.toList());
    }

    @Override
    public PostDto getOne(Long id) {
        return PostDto.from(postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Can't find post with id: " + id)));
    }

    @Override
    public void makeComment(CommentDto form) {
        commentRepository.save(Comment.builder()
                .post(postRepository.getOne(form.getPostId()))
                .user(usersRepository.getOne(form.getUserId()))
                .text(form.getText())
                .build());
    }

    @Override
    public void makeLike(LikeDto form) {
        likeRepository.save(Like.builder()
                .post(postRepository.getOne(form.getPostId()))
                .user(usersRepository.getOne(form.getUserId()))
                .build());
    }

    @Override
    public List<PostDto> getFavoritePosts(Long userId) {
        return postRepository.findAllFavoriteByUserId(userId)
                .stream().map(PostDto::from).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAll() {
        return postRepository.findAll()
                .stream().map(PostDto::from).collect(Collectors.toList());
    }
}
