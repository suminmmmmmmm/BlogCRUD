package com.example.blog.controller;

import com.example.blog.domain.ArticleRequest;
import com.example.blog.domain.ArticleResponse;
import com.example.blog.domain.UpdateRequest;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    // 글 작성
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleRequest articleRequest) {
       Article article = articleService.save(articleRequest);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(article);
    }

    // 글 전체 조회
    @GetMapping("api/articles")
    public ResponseEntity<List<ArticleResponse>> findAll(){
       List<ArticleResponse> articles = articleService.findAll()
               .stream()
               .map(ArticleResponse::new)
               .toList();
       return ResponseEntity.ok(articles);
    }

    // 글 개별 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findOne(@PathVariable("id") long id) {
       Article article = articleService.findOne(id);
       return ResponseEntity.ok()
               .body(new ArticleResponse(article));
    }

    // 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") long id, @RequestBody UpdateRequest articleRequest) {
       Article article = articleService.update(articleRequest, id);
       return ResponseEntity.ok()
               .body(article);
    }


}
