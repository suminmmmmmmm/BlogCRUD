package com.example.blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",nullable = false)
    private Article2 article;

    public Comment(String author, String content, Article2 article) {
        this.author = author;
        this.content = content;
        this.article = article;

    }

    @Builder
    public Comment(Long id, String author, String content, Article2 article) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.article = article;
    }

    public void update(String content) {
        this.content = content;
    }

}
