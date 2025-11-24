package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.CommentRequest;
import com.example.blog.domain.CommentResponse;
import com.example.blog.domain.CommentUpdate;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/article/{articleId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable("articleId") Long articleId,
            @RequestBody CommentRequest request
    ) {
        Comment saved = commentService.addComment(articleId, request);
        return ResponseEntity.status(201).body(new CommentResponse(saved));
        }

    //댓글 목록
    @GetMapping("/api/article/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("articleId") Long articleId){
        List<CommentResponse> comments = commentService.getComments(articleId)
                .stream()
                .map(CommentResponse::new)
                .toList();

        return ResponseEntity.ok(comments);
    }

    //댓글수정
    @PutMapping("/api/comments/{articleId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("articleId") Long id, @RequestBody CommentUpdate update){
       Comment updated = commentService.updateComment(id, update);
       return ResponseEntity.ok(new CommentResponse(updated));
    }

    //댓글삭제
    @DeleteMapping("/api/comments/{articleId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable("articleId") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    }
