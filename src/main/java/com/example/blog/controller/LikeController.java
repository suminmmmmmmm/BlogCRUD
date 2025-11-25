package com.example.blog.controller;

import com.example.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/api/article/{articleId}/like")
    public ResponseEntity<Integer> like(@PathVariable("articleId") Long articleId, @RequestParam("userId") String userId) {
        int likeCount = likeService.toggleLike(articleId, userId);
        return ResponseEntity.ok(likeCount);
    }
}
