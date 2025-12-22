package com.example.blog.domain;

import com.example.blog.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    private String title;
    private String content;

    public Article toEntity() {
       return Article.builder()
               .title(title)
               .content(content)
               .build();
    }

}
