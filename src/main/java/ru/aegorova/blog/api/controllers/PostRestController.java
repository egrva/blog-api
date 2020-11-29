package ru.aegorova.blog.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.aegorova.blog.api.dto.CommentDto;
import ru.aegorova.blog.api.dto.LikeDto;
import ru.aegorova.blog.api.dto.PostDto;
import ru.aegorova.blog.api.security.details.UserDetailsImpl;
import ru.aegorova.blog.api.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/favs")
    public ResponseEntity<List<PostDto>> getFavourites(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.getFavoritePosts(userDetails.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getOne(postId));
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> makeComment(@AuthenticationPrincipal UserDetailsImpl userDetails
            , @PathVariable("postId") Long postId
            , @RequestBody String text) {
        postService.makeComment(CommentDto.builder()
                .postId(postId)
                .text(text)
                .userId(userDetails.getId())
                .build());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> makeLike(@AuthenticationPrincipal UserDetailsImpl userDetails
            , @PathVariable("postId") Long postId) {
        postService.makeLike(LikeDto.builder()
                .postId(postId)
                .userId(userDetails.getId())
                .build());
        return ResponseEntity.accepted().build();
    }

}
