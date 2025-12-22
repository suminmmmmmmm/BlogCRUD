package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "content",nullable = false)
    private String content;

    @Builder
    public Comment(Article article,String author, String content) {
        this.article = article;
        this.author = author;
        this.content = content;
    }



}
