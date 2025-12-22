package com.example.blog.controller;

import com.example.blog.domain.CommentRequest;
import com.example.blog.domain.CommentResponse;
import com.example.blog.entity.Comment;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;

    //댓글 생성
    @PostMapping("/comments/{articleId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("articleId") Long id, @RequestBody CommentRequest request) {
        Comment saved = commentService.saveComment(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommentResponse(saved));
    }

    //댓글 조회
    @GetMapping("/comments/{articleId}")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable("articleId") Long id) {
        List<CommentResponse> comments = commentService.getComments(id)
                .stream()
                .map(CommentResponse::new)
                .toList();
        return ResponseEntity.ok(comments);
    }
}
