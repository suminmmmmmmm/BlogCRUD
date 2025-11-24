package com.example.blog.domain;

import lombok.Getter;

@Getter
public class ArticleResponse2 {

    private String title;
    private String content;

    public ArticleResponse2(Article2 article2) {
        this.title = article2.getTitle();
        this.content = article2.getContent();
    }
}
