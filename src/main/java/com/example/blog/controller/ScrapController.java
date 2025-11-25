package com.example.blog.controller;

import com.example.blog.service.BlogService2;
import com.example.blog.service.ScrapService;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;
    private final BlogService2 blogService;

    @PostMapping("/api/article/{articleId}/scrap")
    public ResponseEntity<Boolean> scrap(@PathVariable("articleId") Long articleId, @RequestParam("userId") String userId) {
      boolean scrapped =  scrapService.toggleScrap(articleId, userId);
      return ResponseEntity.ok(scrapped);
    }
}
