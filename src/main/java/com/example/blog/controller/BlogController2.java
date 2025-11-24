package com.example.blog.controller;


import com.example.blog.domain.AddArticleRequest2;
import com.example.blog.domain.Article2;
import com.example.blog.domain.ArticleResponse2;
import com.example.blog.domain.UpdateArticleRequest2;
import com.example.blog.service.BlogService;
import com.example.blog.service.BlogService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController2 {
    private final BlogService2 blogService2;

    @PostMapping("/api/article")
    public ResponseEntity<Article2> addArticle(@RequestBody AddArticleRequest2 request2) {
        Article2 savedArticle = blogService2.save(request2);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/article")
    public ResponseEntity<List<ArticleResponse2>> findAllArticles() {
        List<ArticleResponse2> articles = blogService2.findAll()
                .stream()
                .map(ArticleResponse2::new)
                .toList();
        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/article/{id}")
    public ResponseEntity<ArticleResponse2> findArticleById(@PathVariable("id") Long id) {
         Article2 article2 =blogService2.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse2(article2));
    }

    @DeleteMapping("/api/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
       blogService2.deleteById(id);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/api/article/{id}")
    public ResponseEntity<Article2>update(@PathVariable("id") long id,@RequestBody UpdateArticleRequest2 updateArticleRequest2){
        Article2 article2 = blogService2.update(id, updateArticleRequest2);

        return ResponseEntity.ok()
                .body(article2);
    }
}
